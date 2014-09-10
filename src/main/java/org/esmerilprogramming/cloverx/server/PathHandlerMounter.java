package org.esmerilprogramming.cloverx.server;

import io.undertow.Handlers;
import io.undertow.server.HttpHandler;
import io.undertow.server.handlers.PathHandler;
import io.undertow.servlet.api.DeploymentInfo;
import io.undertow.servlet.api.DeploymentManager;
import io.undertow.servlet.api.ServletContainer;
import io.undertow.websockets.jsr.WebSocketDeploymentInfo;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;

import org.esmerilprogramming.cloverx.annotation.BeforeTranslate;
import org.esmerilprogramming.cloverx.annotation.Controller;
import org.esmerilprogramming.cloverx.annotation.Page;
import org.esmerilprogramming.cloverx.http.CloverXRequest;
import org.esmerilprogramming.cloverx.scanner.ScannerResult;
import org.jboss.logging.Logger;
import org.xnio.ByteBufferSlicePool;

public class PathHandlerMounter {

  private static final Logger LOGGER = Logger.getLogger(PathHandlerMounter.class);

  public PathHandler mount(ScannerResult scanResult) {
    
    PathHandler pathHandler = Handlers.path();
    try {
      for (Class<?> handlerClass : scanResult.getHandlers()) {
          Constructor<?> constructor = handlerClass.getConstructor();
          constructor.setAccessible(true);
          mountMethods(pathHandler, handlerClass);
      }
      pathHandler = mountServerEndpoints(pathHandler , scanResult.getServerEndpoints() );
    } catch (Exception e) {
      LOGGER.error(e.getMessage());
    }

    return pathHandler;
  }
  
  protected PathHandler mountServerEndpoints(final PathHandler pathHandler , List<Class<?>> serverEndpoints) throws ServletException{
    if(!serverEndpoints.isEmpty()){
      DeploymentInfo builder = new DeploymentInfo()
        .setClassLoader(this.getClass().getClassLoader())
        .setContextPath("/");
      WebSocketDeploymentInfo wsDeployInfo = new WebSocketDeploymentInfo();
      wsDeployInfo.setBuffers(new ByteBufferSlicePool(100, 1000));
      for(Class<?> serverEndpoint : serverEndpoints ){
        wsDeployInfo.addEndpoint( serverEndpoint );
      }
      builder.addServletContextAttribute(WebSocketDeploymentInfo.ATTRIBUTE_NAME, wsDeployInfo );
      builder.setDeploymentName("websocketDeploy.war");
      
      final ServletContainer container = ServletContainer.Factory.newInstance();
      DeploymentManager manager = container.addDeployment(builder);
      manager.deploy();
      pathHandler.addPrefixPath("/", manager.start() );
    }      
    return pathHandler;
  }

  protected PathHandler mountMethods(PathHandler pathHandler, final Class<?> handlerClass) {
    Controller controllerAnnotation = handlerClass.getAnnotation(Controller.class);

    Method[] methods = handlerClass.getMethods();

    final List<Method> beforeTranslationMethods = identifyBeforeTranslationMethod(methods);
    for (final Method method : methods) {
      Page methodPagePath = method.getAnnotation(Page.class);
      if (methodPagePath != null) {
        HttpHandler h = new HandlerCreator()
            .forPageClass(handlerClass)
            .withPathMethod(method)
            .withExecuteBeforeMethods(beforeTranslationMethods)
            .mount();
        
        for (String pageRoot : controllerAnnotation.path()) {
          for (String methodRoot : methodPagePath.value()) {
            pathHandler.addExactPath(pageRoot + "/" + methodRoot, h);
          }
        }
      }
    }

    return pathHandler;
  }
  
  protected List<Method> identifyBeforeTranslationMethod(Method[] methods) {
    List<Method> beforeTranslationMethods = new ArrayList<>();
    for (Method method : methods) {
      List<Annotation> asList = Arrays.asList(method.getAnnotations());
      for (Annotation annotation : asList) {
        if (annotation instanceof BeforeTranslate) {
          beforeTranslationMethods.add(method);
        }
      }
    }
    return beforeTranslationMethods;
  }

  @SuppressWarnings("unchecked")
  protected <T> T setParamater(Class<T> clazz, String parameterName, CloverXRequest request) {
    if (String.class.equals(clazz)) {
      return (T) request.getParameter(parameterName);
    }
    return null;
  }
}
