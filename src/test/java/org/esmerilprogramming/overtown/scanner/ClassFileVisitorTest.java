package org.esmerilprogramming.overtown.scanner;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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
  public void t() throws IOException, URISyntaxException {
    ClassLoader classLoader = this.getClass().getClassLoader();
    ClassFileVisitor visitor = new ClassFileVisitor(classLoader);
    URL url = ClassFileVisitorTest.class.getResource("/");

    Files.walkFileTree(Paths.get( Paths.get( url.toURI()  ).toString() + "/com/clover/scanner/testpackage"), visitor);

    ScannerResult result = visitor.getResult();
    System.out.println(result.getServlets());
  }

  @Test
  public void givenAPathToAClassFileShouldReturnTrue() throws URISyntaxException {
    URL url = ClassFileVisitorTest.class.getResource("/");
    
    Path path = Paths.get( Paths.get( url.toURI()  ).toString() + "/com/clover/scanner/testpackage/First.class");

    assertTrue("Sould return true when it is a class", fileVisitor.isClass(path));
  }

  @Test
  public void givenAPathToANonClassFileShouldReturnFalse() throws URISyntaxException {
    URL url = ClassFileVisitorTest.class.getResource("/");
    Path path = Paths.get( Paths.get( url.toURI()  ).toString() + "/com/clover/scanner/");

    assertFalse("Sould return false when it is not class", fileVisitor.isClass(path));
  }

  @Test
  public void givenAClassFilePathShouldReturnStringWithThePackageAndClass() throws URISyntaxException {
    URL url = ClassFileVisitorTest.class.getResource("/");
    Path path = Paths.get( Paths.get( url.toURI()  ).toString() + "/com/clover/scanner/testpackage/First.class");
   
    String expectedResult = "com.clover.scanner.testpackage.First";
    fileVisitor.setPathToReplace( Paths.get( url.toURI()  ).toString() );
    String result = fileVisitor.asPackageClass(path);

    assertEquals(expectedResult, result);
  }

}
