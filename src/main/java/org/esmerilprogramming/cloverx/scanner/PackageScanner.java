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
import java.util.Set;

import org.esmerilprogramming.cloverx.annotation.Controller;
import org.esmerilprogramming.cloverx.annotation.session.SessionListener;
import org.esmerilprogramming.cloverx.scanner.exception.PackageNotFoundException;
import org.reflections.Reflections;

import javax.servlet.http.HttpServlet;
import javax.websocket.server.ServerEndpoint;

/**
 * 
 * @author efraimgentil (efraim.gentil@gmail.com)
 */
public class PackageScanner {

  public ScannerResult scan( String packageToSearch )
      throws PackageNotFoundException, IOException {
    return scanPackage( packageToSearch );
  }

  protected ScannerResult scanPackage(String packageToSearch) throws PackageNotFoundException, IOException {
    Reflections reflections = new Reflections( packageToSearch );
    Set<Class<?>> handlers = reflections.getTypesAnnotatedWith(Controller.class);
    Set<Class<?>> serverEndpoints = reflections.getTypesAnnotatedWith(ServerEndpoint.class);
    Set<Class<?>> sessionListeners = reflections.getTypesAnnotatedWith(SessionListener.class);
    Set<Class<? extends HttpServlet>> servlets = reflections.getSubTypesOf(HttpServlet.class);

    ScannerResult scannerResult = new ScannerResult();
    for(Class<?> c : handlers){
      scannerResult.addHandlerClass(c);
    }
    for(Class<?> c : serverEndpoints){
      scannerResult.addServerEndpointClass(c);
    }
    for(Class<?> c : sessionListeners){
      scannerResult.addSessionListener(c);
    }
    for(Class<? extends HttpServlet> c : servlets){
      scannerResult.addServletClass(c);
    }

    return scannerResult;
  }

}
