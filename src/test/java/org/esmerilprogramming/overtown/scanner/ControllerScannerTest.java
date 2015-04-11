package org.esmerilprogramming.overtown.scanner;

import org.esmerilprogramming.overtown.server.handlers.ControllerMapping;
import org.esmerilprogramming.overtown.server.handlers.helpers.RestTestController;
import org.esmerilprogramming.overtown.server.handlers.helpers.TestUserController;
import org.esmerilprogramming.overtown.server.handlers.helpers.TestUserPessoaController;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by efraimgentil<efraimgentil@gmail.com> on 29/01/15.
 */
public class ControllerScannerTest {

  ControllerScanner scanner;

  @Before
  public void setUp(){
    scanner = new ControllerScanner();
  }

  @Test
  public void shouldReturnAControllerMappingToThePathWithTheClassName(){
    ControllerMapping mapping = scanner.scanControllerForMapping(RestTestController.class);

    assertEquals( "/restTest" , mapping.getPath() );
  }

  @Test
  public void shouldReturnAControllerMappingWithThePathEspecifiedInTheAnnotation(){
    ControllerMapping mapping = scanner.scanControllerForMapping(TestUserController.class);

    assertEquals( "/user" , mapping.getPath() );
  }

  @Test
  public void shouldReturnTheControllerMappingWithThePathEspecifiedInTheAnnotation(){
    ControllerMapping mapping = scanner.scanControllerForMapping(TestUserPessoaController.class);

    assertEquals( "/user/pessoa" , mapping.getPath() );
  }

}
