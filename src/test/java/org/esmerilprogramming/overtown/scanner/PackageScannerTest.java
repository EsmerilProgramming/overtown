package org.esmerilprogramming.overtown.scanner;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServlet;

import org.esmerilprogramming.overtown.scanner.exception.PackageNotFoundException;
import org.esmerilprogramming.overtown.scanner.testpackage.First;
import org.esmerilprogramming.overtown.scanner.testpackage.Second;
import org.esmerilprogramming.overtown.scanner.testpackage.Third;
import org.esmerilprogramming.overtown.scanner.testpackage.subpack.AnotherSeverEndpoint;
import org.esmerilprogramming.overtown.scanner.testpackage.subpack.Fifth;
import org.esmerilprogramming.overtown.scanner.testpackage.subpack.Fourth;
import org.esmerilprogramming.overtown.server.ConfigurationBuilder;
import org.esmerilprogramming.overtown.server.ConfigurationHolder;
import org.esmerilprogramming.overtown.server.handlers.ControllerMapping;
import org.junit.Before;
import org.junit.Test;

public class PackageScannerTest {

  private PackageScanner scanner;

  @Before
  public void setUp() {
    try {
      ConfigurationHolder.getInstance().prepareConfiguration(new ConfigurationBuilder().build());
    } catch (IOException e) {
      e.printStackTrace();
    }
    scanner = new PackageScanner();
  }

  @Test
  public void givenAPackagedShouldFindAllServerEndpointAnnotatedClassesInThisPackageAndSubPackages()
      throws PackageNotFoundException, IOException {

    ScannerResult pageClasses =
        scanner.scan("org.esmerilprogramming.cloverx.scanner.testpackage");

    List<Class<?>> handlers = pageClasses.getServerEndpoints();
    assertSame(2, handlers.size());
    assertTrue("Should have found the Second.class", handlers.contains(Second.class));
    assertTrue("Should have found the AnotherSeverEndpoint.class", handlers.contains(AnotherSeverEndpoint.class));
  }
  
  @Test
  public void givenAPackagedShouldFindAllControllerAnnotatedClassesInThisPackageAndSubPackages()
      throws PackageNotFoundException, IOException {

    ScannerResult pageClasses =
        scanner.scan("org.esmerilprogramming.cloverx.scanner.testpackage");

    List<ControllerMapping> handlers = pageClasses.getControllerMappings();
    assertSame(2, handlers.size());
    for(ControllerMapping mapping : handlers ){
      boolean found = false;
      if(mapping.getControllerClass().equals(First.class) )
        found = true;
      if(mapping.getControllerClass().equals(Fifth.class) )
        found = true;
      assertTrue("Should find at least one of the controllers" , found);
    }
  }

  @Test
  public void givenAPackagedShouldFindAllHttpServletClassesInThisPackageAndSubPackages()
      throws PackageNotFoundException, IOException {

    ScannerResult pageClasses =
        scanner.scan("org.esmerilprogramming.cloverx.scanner.testpackage");

    List<Class<? extends HttpServlet>> servlets = pageClasses.getServlets();
    assertSame(2, servlets.size());
    assertTrue("Should have found the Third.class", servlets.contains(Third.class));
    assertTrue("Should have found the Fourth.class", servlets.contains(Fourth.class));
  }

}
