package org.esmerilprogramming.cloverx.server.handlers;

import io.undertow.Handlers;
import io.undertow.Undertow;
import io.undertow.server.HttpHandler;
import io.undertow.server.RoutingHandler;
import io.undertow.server.handlers.PathHandler;
import io.undertow.server.session.InMemorySessionManager;
import io.undertow.server.session.SessionAttachmentHandler;
import io.undertow.server.session.SessionListener;
import io.undertow.server.session.SessionManager;

import java.io.IOException;
import java.util.List;

import io.undertow.servlet.api.DeploymentInfo;
import io.undertow.servlet.api.DeploymentManager;
import io.undertow.servlet.api.ServletContainer;
import io.undertow.websockets.WebSocketConnectionCallback;
import io.undertow.websockets.core.AbstractReceiveListener;
import io.undertow.websockets.core.StreamSourceFrameChannel;
import io.undertow.websockets.core.WebSocketChannel;
import io.undertow.websockets.jsr.WebSocketDeploymentInfo;
import io.undertow.websockets.spi.WebSocketHttpExchange;
import org.esmerilprogramming.cloverx.http.CloverXSessionManager;
import org.esmerilprogramming.cloverx.scanner.PackageScanner;
import org.esmerilprogramming.cloverx.scanner.ScannerResult;
import org.esmerilprogramming.cloverx.scanner.exception.PackageNotFoundException;
import org.esmerilprogramming.cloverx.server.CloverXConfiguration;
import org.esmerilprogramming.cloverx.server.ConfigurationHolder;
import org.esmerilprogramming.cloverx.server.PathHandlerMounter;
import org.esmerilprogramming.cloverx.server.ResourceHandlerMounter;
import org.esmerilprogramming.cloverx.server.exception.ConfigurationException;
import org.esmerilprogramming.cloverx.server.exception.NoControllerException;
import org.esmerilprogramming.cloverx.server.mounters.SessionListenerMounter;
import org.esmerilprogramming.cloverx.server.mounters.SessionListenerMounterImpl;
import org.jboss.logging.Logger;
import org.xnio.ByteBufferSlicePool;

import javax.servlet.ServletException;

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
    configureSessionManager(sessionManager, scannerResult.getSessionListeners() );

    return Undertow.builder()
            .addHttpListener( configuration.getPort(), configuration.getHost()  )
            .setHandler( createRootHandler( configuration , scannerResult ) )
            .build();
  }

  public HttpHandler createRootHandler(CloverXConfiguration configuration , ScannerResult scannerResult) {
    PathHandler pathHandler = Handlers.path();
    pathHandler.addPrefixPath("/" + configuration.getAppContext(), createAppHandlers(scannerResult));
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
        pathHandler.addPrefixPath("/" + configuration.getAppContext() + "/ws",
                new SessionAttachmentHandler(  manager.start() , sessionManager.getSessionManager(),
                  sessionManager.getSessionConfig()) );
      } catch (ServletException e) {
        e.printStackTrace();
      }
    }
    pathHandler.addPrefixPath("/" + configuration.getAppContext() + "/" + configuration.getStaticRootPath(), new ResourceHandlerMounter().mount());
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
      RoutingHandler rh = new CustomRoutingHandler(); // Handlers.routing();
      ControllerHandlerCreator chc = new ControllerHandlerCreator();
      for(ControllerMapping mapping : scannerResult.getControllerMappings() ){
        chc.createHandler(mapping , rh);
      }
      return new SessionAttachmentHandler( rh , sessionManager.getSessionManager(),
              sessionManager.getSessionConfig());
    }
    throw new NoControllerException("You should specify at least one controller, verify if you informed the right package to be scanned or see https://github.com/EsmerilProgramming/cloverx for more info");
  }

}
