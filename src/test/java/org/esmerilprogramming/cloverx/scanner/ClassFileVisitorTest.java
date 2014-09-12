package org.esmerilprogramming.cloverx.scanner;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.esmerilprogramming.cloverx.scanner.ClassFileVisitor;
import org.esmerilprogramming.cloverx.scanner.ScannerResult;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

public class ClassFileVisitorTest {

  private ClassFileVisitor fileVisitor;

  private ClassLoader classLoader;

  @Before
  public void setUp() {
    classLoader = mock(ClassLoader.class);
    fileVisitor = new ClassFileVisitor(classLoader);
  }

  @Test
  @Ignore
  public void t() throws IOException {
    ClassLoader classLoader = this.getClass().getClassLoader();
    ClassFileVisitor visitor = new ClassFileVisitor(classLoader);
    URL url = ClassFileVisitorTest.class.getResource("/");

    Files.walkFileTree(Paths.get(url.getPath() + "/com/clover/scanner/testpackage"), visitor);

    ScannerResult result = visitor.getResult();

    System.out.println(result.getHandlers());
    System.out.println(result.getServlets());
  }

  @Test
  public void givenAPathToAClassFileShouldReturnTrue() {
    URL url = ClassFileVisitorTest.class.getResource("/");
    Path path = Paths.get(url.getPath() + "/com/clover/scanner/testpackage/First.class");

    assertTrue("Sould return true when it is a class", fileVisitor.isClass(path));
  }

  @Test
  public void givenAPathToANonClassFileShouldReturnFalse() {
    URL url = ClassFileVisitorTest.class.getResource("/");
    Path path = Paths.get(url.getPath() + "/com/clover/scanner/");

    assertFalse("Sould return false when it is not class", fileVisitor.isClass(path));
  }

  @Test
  public void givenAClassFilePathShouldReturnStringWithThePackageAndClass() {
    URL url = ClassFileVisitorTest.class.getResource("/");
    Path path = Paths.get(url.getPath() + "/com/clover/scanner/testpackage/First.class");
    String expectedResult = "com.clover.scanner.testpackage.First";
    
    fileVisitor.setPathToReplace( url.getPath() );
    String result = fileVisitor.asPackageClass(path);

    assertEquals(expectedResult, result);
  }

}
