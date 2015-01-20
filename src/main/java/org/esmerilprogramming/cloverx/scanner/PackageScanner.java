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

  public ScannerResult scan(String packageToSearch, ClassLoader classLoader)
      throws PackageNotFoundException, IOException {
    ClassFileVisitor visitor = new ClassFileVisitor(classLoader);
    return scanPackage(packageToSearch, visitor, classLoader);
  }

  protected ScannerResult scanPackage(String packageToSearch, ClassFileVisitor visitor,
      ClassLoader classLoader) throws PackageNotFoundException, IOException {
    Reflections reflections = new Reflections("");
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
/*    try{
      tryToReadJarFile(packageToSearch , visitor , classLoader );
    }catch(Exception e){
      System.out.println("It is not a compresed file, trying to scan the classpath now");
      try {
        loadWithDefaultFileSystem(packageToSearch , visitor , classLoader );
      } catch (URISyntaxException e1) {
        e1.printStackTrace();
      }
    }*/
    return scannerResult; //visitor.getResult();
  }

  protected void tryToReadJarFile(String packageToSearch, ClassFileVisitor visitor, ClassLoader classLoader) throws IOException {
    File f = new File( System.getProperty("user.dir") + File.separator + System.getProperty("java.class.path") );
    URI uri = f.toURI();
    FileSystem fs  = FileSystems.newFileSystem( Paths.get( uri ) , classLoader );
    Path startPath = fs.getPath("/" + packageToSearch.replaceAll("\\.",  "/" ) );
    Files.walkFileTree(startPath, visitor);
  }

  protected void loadWithDefaultFileSystem(String packageToSearch, ClassFileVisitor visitor, ClassLoader classLoader) throws PackageNotFoundException, IOException, URISyntaxException {
    if (!"".equals(packageToSearch)) {
      packageToSearch = packageToSearch.replaceAll("\\.", "/");
    }
    URL currentClassPath = this.getClass().getResource("/" + packageToSearch);
    if (currentClassPath == null) {
      throw new PackageNotFoundException("Was not possible to find the especified ("
              + packageToSearch + ") package to scan");
    }
    Path path = Paths.get(currentClassPath.toURI());
     visitor.setPathToReplace( Paths.get( this.getClass().getResource("/").toURI() ).toString() );
     Files.walkFileTree(path, visitor);
  }

}
