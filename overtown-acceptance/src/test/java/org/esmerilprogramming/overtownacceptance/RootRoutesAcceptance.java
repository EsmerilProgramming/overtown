package org.esmerilprogramming.overtownacceptance;

import org.esmerilprogramming.overtownacceptance.main.MainWithContext;
import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import static org.junit.Assert.assertTrue;

/**
 * Created by efraimgentil<efraimgentil@gmail.com> on 04/05/15.
 */
public class RootRoutesAcceptance {

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
  public void doesRespondsToGetWithEndSlash() throws InterruptedException{
    webDriver.get("localhost:8080/acceptance/");
    String pageSource = webDriver.getPageSource();
    assertTrue(  pageSource.contains("GET - index/index"));
  }

  @Test
  public void doesRespondsToGetWithouEndSlash() throws InterruptedException{
    webDriver.get("localhost:8080/acceptance");
    String pageSource = webDriver.getPageSource();
    assertTrue(  pageSource.contains("GET - index/index"));
  }

}
