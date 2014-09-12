package org.esmerilprogramming.cloverx.scanner;

import java.io.IOException;
import java.net.URL;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.regex.Pattern;

import org.jboss.logging.Logger;

public class ClassFileVisitor extends SimpleFileVisitor<Path> {

  private static final Logger LOGGER = Logger.getLogger(ClassFileVisitor.class);

  private ScannerResult result;

  private ClassLoader classLoader;
  private Pattern classPatern = Pattern.compile("\\.class$");
  private String pathToReplace = null;

  public ClassFileVisitor(ClassLoader classLoader) {
    this.classLoader = classLoader;
  }

  @Override
  public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
    if (isClass(file)) {
      LOGGER.info(file.toString());
      try {
//        System.out.println("scanning file: " + file );
//        System.out.println("scanning as package: " + asPackageClass(file) );
        Class<?> loadedClass = classLoader.loadClass(asPackageClass(file));
        getResult().addClass(loadedClass);
      } catch (ClassNotFoundException e) {
        LOGGER.error(e.getMessage());
      }
    }
    return super.visitFile(file, attrs);
  }

  public boolean isClass(Path file) {
    return classPatern.matcher(file.getFileName().toString()).find();
  }

  public String asPackageClass(Path file) {
    String strPackage = file.toString();
    if(getPathToReplace() != null){
      strPackage = strPackage.replace( getPathToReplace() , "");
    }
    String asPackaged = strPackage.replaceAll("\\/|\\\\", ".").replace(".class", "");
    if( asPackaged.charAt(0) == ".".charAt(0))
      asPackaged = asPackaged.substring(1);
    return asPackaged;
  }

  public ScannerResult getResult() {
    if (result == null) {
      result = new ScannerResult();
    }
    return result;
  }

  public String getPathToReplace() {
    return pathToReplace;
  }

  public void setPathToReplace(String pathToReplace) {
    this.pathToReplace = pathToReplace;
  }

}
