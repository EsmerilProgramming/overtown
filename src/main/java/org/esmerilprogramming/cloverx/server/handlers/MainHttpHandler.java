package org.esmerilprogramming.cloverx.server.handlers;

import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import org.esmerilprogramming.cloverx.http.CloverXRequest;

import java.lang.reflect.Method;

/**
 * Created by efraimgentil<efraimgentil@gmail.com> on 28/01/15.
 */
public class MainHttpHandler implements HttpHandler {

  private Class<?> controller;


  @Override
  public void handleRequest(HttpServerExchange exchange) throws Exception {
     CloverXRequest request =  new CloverXRequest(exchange);

       request.getHttpMethod();



  }

  protected Method getMethodForHttpVerb(String httpMethod){
     return null;
  }

}
