package org.esmerilprogramming.cloverx.scanner;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.CodeSource;

import org.esmerilprogramming.cloverx.scanner.exception.PackageNotFoundException;

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

    Class<? extends PackageScanner> thisClass = this.getClass();
    CodeSource src = this.getClass().getProtectionDomain().getCodeSource();

    URL jar = src.getLocation();
    URL resource = thisClass.getResource("");
    if (resource.getProtocol().equals("file")) {
      if (!"".equals(packageToSearch)) {
        packageToSearch = packageToSearch.replaceAll("\\.", "/");
      }
      URL currentClassPath = thisClass.getResource("/" + packageToSearch);
      if (currentClassPath == null)
        throw new PackageNotFoundException("Was not possible to find the especified ("
            + packageToSearch + ") package to scan");
      try {
        Path path = Paths.get(currentClassPath.toURI());
        visitor.setPathToReplace( Paths.get( thisClass.getResource("/").toURI() ).toString() );
        Files.walkFileTree(path, visitor);
      } catch (URISyntaxException e) {
        e.printStackTrace();
      }
    } else if (resource.getProtocol().equals("jar")) {
      try {
        jar = new URL("file://" + System.getProperty("user.dir") + "/" + System.getProperty("java.class.path"));
        FileSystem fs = FileSystems.newFileSystem(  Paths.get(jar.toURI() ), null);
        Path startPath = fs.getPath("/");
        Files.walkFileTree(startPath, visitor);
      } catch (URISyntaxException e1) {
        e1.printStackTrace();
      }
    }
    return visitor.getResult();
  }

}
