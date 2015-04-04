package org.esmerilprogramming.cloverxacceptance;

import org.esmerilprogramming.cloverxacceptance.main.MainApp;
import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import static org.junit.Assert.assertTrue;

/**
 * Created by efraimgentil<efraimgentil@gmail.com> on 15/03/15.
 */
public class PutRoutesAcceptance {

  WebDriver webDriver;
  static MainApp mainApp;

  @BeforeClass
  public static void initClass(){
    mainApp = new MainApp();
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

    //  webDriver.quit();

  }

  @Test
  public void doesCallPutAndSendPutMethodResponseToUse(){
    webDriver.get("localhost:8080/acceptance/index/indexWithTemplate");
    WebElement name = webDriver.findElement(By.id("putName"));
    name.sendKeys("efraim");
    webDriver.findElement(By.id("putSubmit")).click();

    String pageSource = webDriver.getPageSource();
    System.out.println( pageSource );
    assertTrue(  pageSource.contains("PUT - index/put - nome:efraim") );
  }

}