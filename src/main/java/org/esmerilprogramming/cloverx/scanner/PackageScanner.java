package org.esmerilprogramming.cloverx.scanner;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashSet;
import java.util.Set;

import org.esmerilprogramming.cloverx.annotation.Controller;
import org.esmerilprogramming.cloverx.annotation.session.SessionListener;
import org.esmerilprogramming.cloverx.scanner.exception.PackageNotFoundException;
import org.esmerilprogramming.cloverx.server.CloverXConfiguration;
import org.esmerilprogramming.cloverx.server.ConfigurationHolder;
import org.reflections.Reflections;
import org.reflections.Store;

import javax.servlet.http.HttpServlet;
import javax.websocket.server.ServerEndpoint;

/**
 * 
 * @author efraimgentil (efraim.gentil@gmail.com)
 */
public class PackageScanner {

  private CloverXConfiguration configuration;

  public ScannerResult scan( String packageToSearch )
      throws PackageNotFoundException, IOException {
    configuration = ConfigurationHolder.getInstance().getConfiguration();
    return scanPackage( packageToSearch );
  }

  protected ScannerResult scanPackage(String packageToSearch) throws PackageNotFoundException, IOException {
    Reflections reflections = new Reflections( packageToSearch );
    Store store = reflections.getStore();
    Set<Class<?>> handlers = reflections.getTypesAnnotatedWith(Controller.class);
    Set<Class<?>> serverEndpoints = reflections.getTypesAnnotatedWith(ServerEndpoint.class);
    Set<Class<?>> sessionListeners = reflections.getTypesAnnotatedWith(SessionListener.class);
    Set<Class<? extends HttpServlet>> servlets = reflections.getSubTypesOf(HttpServlet.class);

    ScannerResult scannerResult = new ScannerResult();
    for(Class<?> c : filtrateClasses(handlers) ){
      scannerResult.addHandlerClass(c);
    }
    for(Class<?> c : filtrateClasses(serverEndpoints) ){
      scannerResult.addServerEndpointClass(c);
    }
    for(Class<?> c : filtrateClasses(sessionListeners) ){
      scannerResult.addSessionListener(c);
    }
    for(Class c : filtrateClasses( servlets )  ){
      scannerResult.addServletClass(c);
    }

    return scannerResult;
  }

  protected <T> Set<Class<? extends T>> filtrateClasses(Set<Class<? extends T>> classes){
    if( !configuration.getRunManagement() ){
      Set<Class<?>> classesToRemove = new LinkedHashSet<>();
      for ( Class<?> c : classes){
        if( c.getPackage().getName().startsWith("org.esmerilprogramming.cloverx.management") ){
          classesToRemove.add(c);
        }
      }
      classes.removeAll( classesToRemove );
    }
    return classes;
  }

}
