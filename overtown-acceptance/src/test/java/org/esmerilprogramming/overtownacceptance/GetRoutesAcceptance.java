package org.esmerilprogramming.overtownacceptance;

import org.esmerilprogramming.overtownacceptance.main.MainWithContext;
import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import static org.junit.Assert.*;

/**
 * Created by efraimgentil<efraimgentil@gmail.com> on 15/03/15.
 */
public class GetRoutesAcceptance {

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
  public void doesGetIndexWithoutTemplatePage() throws InterruptedException{
    webDriver.get("localhost:8080/acceptance/get/index");
    String pageSource = webDriver.getPageSource();
    assertTrue(  pageSource.contains("GET - get/index"));
  }

  @Test
  public void doesGetIndexWithTemplatePage() throws InterruptedException{
    webDriver.get("localhost:8080/acceptance/get/indexWithTemplate");
    WebElement title = webDriver.findElement(By.id("pageTitle"));
    assertTrue("Index with template".equalsIgnoreCase(title.getText()));
  }

  @Test
  public void doesGetIndexWithRootTemplatePage() throws InterruptedException{
    webDriver.get("localhost:8080/acceptance/get/indexWithRootTemplate");
    WebElement title = webDriver.findElement(By.id("pageTitle"));
    assertTrue("Root Index Template".equalsIgnoreCase(title.getText()));
  }

}
