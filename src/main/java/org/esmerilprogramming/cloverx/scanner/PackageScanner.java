package org.esmerilprogramming.cloverx.scanner;

import org.esmerilprogramming.cloverx.annotation.Controller;
import org.esmerilprogramming.cloverx.annotation.path.MethodNotAllowed;
import org.esmerilprogramming.cloverx.annotation.path.NotFound;
import org.esmerilprogramming.cloverx.annotation.session.SessionListener;
import org.esmerilprogramming.cloverx.http.ErrorHandler;
import org.esmerilprogramming.cloverx.scanner.exception.PackageNotFoundException;
import org.esmerilprogramming.cloverx.server.CloverXConfiguration;
import org.esmerilprogramming.cloverx.server.ConfigurationHolder;
import org.jboss.logging.Logger;
import org.reflections.Reflections;

import javax.servlet.http.HttpServlet;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * 
 * @author efraimgentil (efraim.gentil@gmail.com)
 */
public class PackageScanner {

  private CloverXConfiguration configuration;

  private final String MANAGEMENT_PACKAGE = "org.esmerilprogramming.cloverx.management";
  private final Logger logger = Logger.getLogger(PackageScanner.class);

  public ScannerResult scan( String packageToSearch )
      throws PackageNotFoundException, IOException {
    configuration = ConfigurationHolder.getInstance().getConfiguration();
    return scanPackage( packageToSearch );
  }

  protected ScannerResult scanPackage(String packageToSearch) throws PackageNotFoundException, IOException {
    Reflections reflections = new Reflections( packageToSearch );

    Set<Class<?>> controllers = reflections.getTypesAnnotatedWith(Controller.class);
    Set<Class<?>> serverEndpoints = reflections.getTypesAnnotatedWith(ServerEndpoint.class);
    Set<Class<?>> sessionListeners = reflections.getTypesAnnotatedWith(SessionListener.class);
    Set<Class<? extends HttpServlet>> servlets = reflections.getSubTypesOf(HttpServlet.class);


    ScannerResult scannerResult = new ScannerResult();
    scannerResult = mapControllers( scannerResult , filtrateClasses(controllers) );
    for(Class<?> c : filtrateClasses(serverEndpoints) ){
      scannerResult.addServerEndpointClass(c);
    }
    for(Class<?> c : filtrateClasses(sessionListeners) ){
      scannerResult.addSessionListener(c);
    }
    for(Class c : filtrateClasses( servlets )  ){
      scannerResult.addServletClass(c);
    }
    scannerResult.setNotFoundClass( findErrorHandler( NotFound.class , reflections )  );
    scannerResult.setMethodNotAllowedClass( findErrorHandler(MethodNotAllowed.class, reflections) );

    return scannerResult;
  }

  protected Class<? extends ErrorHandler> findErrorHandler( Class annotationClass , Reflections reflections){
    Set<Class<?>> result = reflections.getTypesAnnotatedWith( annotationClass );
    if(result.size() >= 1){
      Class c = result.iterator().next();
      if(!Arrays.asList( c.getInterfaces() ).contains(ErrorHandler.class)){
        logger.warn("The class '" + c.getName() + "' is mapped as @" + annotationClass.getSimpleName()  +" but doesn't implements org.esmerilprogramming.cloverx.http.ErrorHandler it will be disconsidered");
      }else{
        if(result.size() > 2){
          logger.info("Was found more than one class with @NotFound annotation, only the first will be considered");
        }
        return c;
      }
    }
    return null;
  }

  protected ScannerResult mapControllers( ScannerResult scannerResult , Set<Class<?>> controllers){
    ControllerScanner scanner = new ControllerScanner();
    for(Class<?> controller : controllers){
       scannerResult.addControllerMapping( scanner.scanControllerForMapping(controller) );
    }
    return scannerResult;
  }

  protected <T> Set<Class<? extends T>> filtrateClasses(Set<Class<? extends T>> classes){
    if( !configuration.getRunManagement() ){
      Set<Class<?>> classesToRemove = new LinkedHashSet<>();
      for ( Class<?> c : classes){
        if( c.getPackage().getName().startsWith( MANAGEMENT_PACKAGE ) ){
          classesToRemove.add(c);
        }
      }
      classes.removeAll( classesToRemove );
    }
    return classes;
  }

}
