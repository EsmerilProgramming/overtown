package org.esmerilprogramming.overtownacceptance;

import com.gargoylesoftware.htmlunit.html.HtmlTextInput;
import org.esmerilprogramming.overtownacceptance.main.MainApp;
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
    webDriver.quit();
  }

  @Test
  public void doesGetIndexWithoutTemplatePage() throws InterruptedException{
    webDriver.get("localhost:8080/acceptance/index/index");
    String pageSource = webDriver.getPageSource();
    assertTrue(  pageSource.contains("GET - index/index"));
  }

  @Test
  public void doesGetIndexWithTemplatePage() throws InterruptedException{
    webDriver.get("localhost:8080/acceptance/index/indexWithTemplate");
    WebElement title = webDriver.findElement(By.id("pageTitle"));
    assertTrue("Index with template".equalsIgnoreCase(title.getText()));
  }

  @Test
  public void doesGetIndexWithRootTemplatePage() throws InterruptedException{
    webDriver.get("localhost:8080/acceptance/index/indexWithRootTemplate");
    WebElement title = webDriver.findElement(By.id("pageTitle"));
    assertTrue("Root Index Template".equalsIgnoreCase(title.getText()));
  }

  @Test
  public void doesCallPostAndSendPostMethodResponseToUse(){
    webDriver.get("localhost:8080/acceptance/index/indexWithTemplate");
    WebElement name = webDriver.findElement(By.id("name"));
    name.sendKeys("efraim");
    webDriver.findElement(By.id("submit")).click();

    String pageSource = webDriver.getPageSource();
    System.out.println( pageSource );
    assertTrue(  pageSource.contains("POST - index/index - nome:efraim"));
  }

}
