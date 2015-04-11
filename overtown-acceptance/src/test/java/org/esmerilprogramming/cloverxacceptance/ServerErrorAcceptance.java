package org.esmerilprogramming.cloverxacceptance;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
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
 * Created by efraimgentil<efraimgentil@gmail.com> on 15/03/15.
 */
public class ServerErrorAcceptance {

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
  public void doesCallSendBackErroPageWhenThereAnyNotCaughtException(){
    webDriver.get("localhost:8080/acceptance/serverError/throwError");

    String pageSource = webDriver.getPageSource().toUpperCase();
    assertTrue(  pageSource.contains("500 INTERNAL SERVER ERROR") );
  }


}