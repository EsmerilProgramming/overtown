package org.esmerilprogramming.overtownacceptance;

import org.esmerilprogramming.overtownacceptance.main.MainWithContext;
import org.junit.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import static org.junit.Assert.assertTrue;

/**
 * Created by efraimgentil<efraimgentil@gmail.com> on 15/03/15.
 */
public class ServerErrorAcceptance {

  WebDriver webDriver;
  static MainWithContext mainApp;

  @BeforeClass
  public static void initClass(){
    mainApp = new MainWithContext();
    mainApp.start();
  }

  @AfterClass
  public static void finish(){
    mainApp.stop();
  }

  @Before
  public void initTest(){
    webDriver = new FirefoxDriver();
  }

  @After
  public void endTest(){
    webDriver.quit();
  }

  @Test
  public void doesCallSendBackErroPageWhenThereAnyNotCaughtException(){
    webDriver.get("localhost:8080/acceptance/serverError/throwError");

    String pageSource = webDriver.getPageSource().toUpperCase();
    assertTrue(  pageSource.contains("500 INTERNAL SERVER ERROR") );
  }


}
