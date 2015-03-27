package org.esmerilprogramming.cloverx.server.handlers;

import com.google.common.base.Predicate;
import com.thoughtworks.paranamer.BytecodeReadingParanamer;
import com.thoughtworks.paranamer.CachingParanamer;
import com.thoughtworks.paranamer.Paranamer;
import io.undertow.Handlers;
import io.undertow.Undertow;
import io.undertow.server.HttpHandler;
import io.undertow.server.RoutingHandler;
import io.undertow.server.handlers.PathHandler;
import io.undertow.server.session.InMemorySessionManager;
import io.undertow.server.session.SessionAttachmentHandler;
import io.undertow.server.session.SessionListener;
import io.undertow.server.session.SessionManager;
import io.undertow.servlet.api.DeploymentInfo;
import io.undertow.servlet.api.DeploymentManager;
import io.undertow.servlet.api.ServletContainer;
import io.undertow.websockets.jsr.WebSocketDeploymentInfo;
import org.esmerilprogramming.cloverx.annotation.Page;
import org.esmerilprogramming.cloverx.annotation.path.Get;
import org.esmerilprogramming.cloverx.annotation.path.Path;
import org.esmerilprogramming.cloverx.annotation.path.Post;
import org.esmerilprogramming.cloverx.http.*;
import org.esmerilprogramming.cloverx.scanner.PackageScanner;
import org.esmerilprogramming.cloverx.scanner.ScannerResult;
import org.esmerilprogramming.cloverx.scanner.exception.PackageNotFoundException;
import org.esmerilprogramming.cloverx.server.CloverXConfiguration;
import org.esmerilprogramming.cloverx.server.ConfigurationHolder;
import org.esmerilprogramming.cloverx.server.ResourceHandlerMounter;
import org.esmerilprogramming.cloverx.server.exception.ConfigurationException;
import org.esmerilprogramming.cloverx.server.exception.NoControllerException;
import org.esmerilprogramming.cloverx.server.mounters.SessionListenerMounter;
import org.esmerilprogramming.cloverx.server.mounters.SessionListenerMounterImpl;
import org.jboss.logging.Logger;
import org.reflections.ReflectionUtils;
import org.xnio.ByteBufferSlicePool;

import javax.servlet.ServletException;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;

public class StartupHandlerImpl implements StartupHandler {
  
  private static final Logger LOGGER = Logger.getLogger(StartupHandlerImpl.class);
  
  @Override
  public Undertow prepareBuild(CloverXConfiguration configuration) throws IOException {
    validateConfiguration(configuration);
    ConfigurationHolder.getInstance().prepareConfiguration(configuration);
    ScannerResult scannerResult = identifyEligibleClasses( configuration );
    
    CloverXSessionManager instance = CloverXSessionManager.getInstance();
    InMemorySessionManager sessionManager = instance.getSessionManager();
    sessionManager.setDefaultSessionTimeout( configuration.getMaxSessionTime() );
    configureSessionManager(sessionManager, scannerResult.getSessionListeners());

    return Undertow.builder()
            .addHttpListener( configuration.getPort(), configuration.getHost()  )
            .setHandler( createRootHandler( configuration , scannerResult ) )
            .build();
  }

  public HttpHandler createRootHandler(CloverXConfiguration configuration , ScannerResult scannerResult) {
    PathHandler pathHandler = Handlers.path();

    String appContext = "/" + configuration.getAppContext();
    pathHandler.addPrefixPath( appContext , createAppHandlers(scannerResult));
    if(!scannerResult.getServerEndpoints().isEmpty()){
      DeploymentInfo builder = new DeploymentInfo().setClassLoader(this.getClass().getClassLoader()).setContextPath("/");
      WebSocketDeploymentInfo wsDeployInfo = new WebSocketDeploymentInfo();
      wsDeployInfo.setBuffers(new ByteBufferSlicePool(100, 1000));
      for(Class<?> serverEndpoint : scannerResult.getServerEndpoints() ){
        wsDeployInfo.addEndpoint( serverEndpoint );
      }
      builder.addServletContextAttribute(WebSocketDeploymentInfo.ATTRIBUTE_NAME, wsDeployInfo);
      builder.setDeploymentName("websocketDeploy.war");

      final ServletContainer container = ServletContainer.Factory.newInstance();
      DeploymentManager manager = container.addDeployment(builder);
      manager.deploy();
      try {
        CloverXSessionManager sessionManager = CloverXSessionManager.getInstance();
        String wsContextPath = "ws";
        if( !appContext.endsWith("/") ){
          wsContextPath += appContext + "/" + wsContextPath;
        }
        pathHandler.addPrefixPath( wsContextPath ,
                new SessionAttachmentHandler(  manager.start() , sessionManager.getSessionManager(),
                  sessionManager.getSessionConfig()) );
      } catch (ServletException e) {
        e.printStackTrace();
      }
    }
    String staticContextPath = configuration.getStaticRootPath();
    if( !appContext.endsWith("/") ){
      staticContextPath += appContext + "/" + staticContextPath;
    }
    pathHandler.addPrefixPath( staticContextPath , new ResourceHandlerMounter().mount());
    return pathHandler;
  }

  public ScannerResult identifyEligibleClasses(CloverXConfiguration configuration) {
    ScannerResult scan = null;
    try {
      scan = new PackageScanner().scan( configuration.getPackageToSkan() );
    } catch (PackageNotFoundException | IOException e) {
      e.printStackTrace();
      LOGGER.error(e.getMessage());
    }
    return scan;
  }
  
  protected void configureSessionManager(SessionManager sessionManager , List<Class<?>> annotatedSessionListeners ){
    SessionListenerMounter mounter = new SessionListenerMounterImpl();
    for (Class<?> annotatedClass : annotatedSessionListeners) {
      SessionListener sl = mounter.mount(annotatedClass);
      if(sl != null){
        sessionManager.registerSessionListener( sl);
      }
    }
  }

  protected void validateConfiguration(CloverXConfiguration configuration) {
    String packageToSkan = configuration.getPackageToSkan();
    packageToSkan = packageToSkan == null ? "" : packageToSkan.trim();
    if( "".equals( packageToSkan ) ){
      throw new ConfigurationException("You should specify the package to be scanned. See https://github.com/EsmerilProgramming/cloverx for more info");
    }
  }
  
  public HttpHandler createAppHandlers(ScannerResult scannerResult){
    CloverXSessionManager sessionManager = CloverXSessionManager.getInstance();
    if( !scannerResult.getControllerMappings().isEmpty() ) {
      RoutingHandler rh = new CustomRoutingHandler();
      rh.setFallbackHandler(  mount404( scannerResult.getNotFoundClass() ) );
      rh.setInvalidMethodHandler( mount405( scannerResult.getMethodNotAllowedClass() ) );
      ControllerHandlerCreator chc = new ControllerHandlerCreator();
      for(ControllerMapping mapping : scannerResult.getControllerMappings() ){
        chc.createHandler(mapping , rh);
      }
      return new SessionAttachmentHandler( rh , sessionManager.getSessionManager(),
              sessionManager.getSessionConfig());
    }
    throw new NoControllerException("You should specify at least one controller, verify if you informed the right package to be scanned or see https://github.com/EsmerilProgramming/cloverx for more info");
  }

  protected HttpHandler mount404( Class notFoundClass ){
    return createErrorController("404" , notFoundClass , DefaultNotFoundPage.class );
  }

  protected HttpHandler mount405( Class methodNotAllowed ){
    return createErrorController("405" , methodNotAllowed , DefaultMethodNotAllowedPage.class );
  }

  protected HttpHandler createErrorController(String error , Class<?> clazz , Class defaultClass){
    clazz = clazz == null ? defaultClass : clazz;
    ControllerMapping controllerMapping = new ControllerMapping( error , error);
    controllerMapping.setControllerClass( clazz );
    try {
      Method handlerError = clazz.getMethod("handleError", CloverXRequest.class);
      PathMapping methodMapping = new PathMapping( error , null , handlerError , getTemplate(handlerError) , false );
      Paranamer paranamer = new CachingParanamer(new BytecodeReadingParanamer());
      return new MainHttpHandler( controllerMapping , methodMapping , paranamer.lookupParameterNames( handlerError ) );
    } catch (NoSuchMethodException e) {
      e.printStackTrace();
    }
    return null;
  }

  private String getTemplate( Method notFound ){
    Set<Annotation> annotations = ReflectionUtils.getAnnotations(notFound, new Predicate<Annotation>() {
      public boolean apply(Annotation input) {
        Class<? extends Annotation> aClass = input.annotationType();
        return Path.class.equals(aClass) && !((Path) input).template().equals(Path.NO_TEMPLATE)
                || Get.class.equals(aClass) && !((Get) input).template().equals(Path.NO_TEMPLATE)
                || Post.class.equals(aClass) && !((Post) input).template().equals(Path.NO_TEMPLATE)
                || Page.class.equals(aClass) && !((Page) input).responseTemplate().equals(Path.NO_TEMPLATE);
      }
    });

    String template = Path.NO_TEMPLATE;
    if(annotations.size() > 0) {
      Annotation next = annotations.iterator().next();
      if (Path.class.equals(next.annotationType())) {
        template = ((Path) next).template();
      }
      if (Get.class.equals(next.annotationType())) {
        template = ((Get) next).template();
      }
      if (Post.class.equals(next.annotationType())) {
        template = ((Post) next).template();
      }
      if (Page.class.equals(next.annotationType())) {
        template = ((Page) next).responseTemplate();
      }
    }
    return template;
  }

}
