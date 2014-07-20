package org.esmerilprogramming.cloverx.scanner;

import java.io.IOException;
import java.net.URL;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.regex.Pattern;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClassFileVisitor extends SimpleFileVisitor<Path> {

  private ScannerResult result;

  private ClassLoader classLoader;
  private Pattern classPatern = Pattern.compile("\\.class$");

  public ClassFileVisitor(ClassLoader classLoader) {
    this.classLoader = classLoader;
  }

  @Override
  public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
    Logger.getLogger(ClassFileVisitor.class.getSimpleName()).log(Level.INFO, file.toString());
    if (isClass(file)) {
      try {
        Class<?> loadedClass = classLoader.loadClass(asPackageClass(file));
        getResult().addClass(loadedClass);
      } catch (ClassNotFoundException e) {
        Logger.getLogger(ClassFileVisitor.class.getName()).log(Level.SEVERE, e.getMessage());
      }
    }
    return super.visitFile(file, attrs);
  }

  public boolean isClass(Path file) {
    return classPatern.matcher(file.getFileName().toString()).find();
  }

  public String asPackageClass(Path file) {
    URL url = ClassFileVisitor.class.getResource("/");
    String strPackage = file.toString().replace(url.getPath(), "");
    return strPackage.replaceAll("\\/", ".").replace(".class", "");
  }

  public ScannerResult getResult() {
    if (result == null) {
      result = new ScannerResult();
    }
    return result;
  }

}
