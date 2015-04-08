package org.esmerilprogramming.cloverxacceptance;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.esmerilprogramming.cloverxacceptance.main.MainApp;
import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * Created by efraimgentil<efraimgentil@gmail.com> on 05/04/15.
 */
public class DeleteRoutesAcceptance {

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
  public void doesCallDeleteThroughGetRequestWithHiddenMethodInformation(){
    webDriver.get("localhost:8080/acceptance/delete/index");
    WebElement name = webDriver.findElement(By.id("deleteGetId"));
    name.sendKeys("1");
    webDriver.findElement(By.id("deleteGetSubmit")).click();

    String pageSource = webDriver.getPageSource();
    System.out.println( pageSource );
    assertTrue(  pageSource.contains("DELETE - delete/delete - id:1") );
  }

  @Test
  public void doesCallDeleteThroughPostRequestWithHiddenMethodInformation(){
    webDriver.get("localhost:8080/acceptance/delete/index");
    WebElement name = webDriver.findElement(By.id("deletePostId"));
    name.sendKeys("1");
    webDriver.findElement(By.id("deletePostSubmit")).click();

    String pageSource = webDriver.getPageSource();
    System.out.println( pageSource );
    assertTrue(pageSource.contains("DELETE - delete/delete - id:1"));
  }

  @Test
  public void doesCorrectRespondToARequestUsingDeleteMethod() throws IOException {
    CloseableHttpClient client = HttpClients.createDefault();
    HttpDelete method = new HttpDelete("http://localhost:8080/acceptance/delete/delete?id=1");

    HttpResponse response = client.execute(method);
    BufferedReader br = new BufferedReader(
            new InputStreamReader((response.getEntity().getContent())));
    String output;
    StringBuilder sb = new StringBuilder();
    while ((output = br.readLine()) != null) {
      sb.append(output);
    }
    assertTrue( sb.toString().contains("DELETE - delete/delete - id:1") ) ;
    client.close();
  }

}
