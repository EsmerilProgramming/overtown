package org.esmerilprogramming.cloverx.http;

import io.undertow.server.HttpServerExchange;
import io.undertow.server.ServerConnection;
import io.undertow.server.protocol.http.HttpServerConnection;
import io.undertow.util.HeaderMap;
import io.undertow.util.HeaderValues;
import io.undertow.util.Headers;

import org.esmerilprogramming.cloverx.view.ViewAttributes;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ResponseTest {
  
  
  private HeaderMap headerMap;
  private Response response;
  @Mock ServerConnection connection;
  HttpServerExchange exchange; 
  
  @Before
  public void setUp(){
    exchange = new HttpServerExchange( connection );
    response = new JsonResponse(exchange , new ViewAttributes() );
    headerMap = new HeaderMap();
    headerMap.add(Headers.CONTENT_TYPE ,  "application/json; charset=UTF-8;" );
  }

  @Test
  public void doesReplaceTheCurrentCharsetWIthTheNewCharset(){
    exchange.getResponseHeaders().add( Headers.CONTENT_TYPE , "application/json; charset=ISO-9981-0" );
    
    response.setCharset("UTF-8");
    
    String headerValue = exchange.getResponseHeaders().get( Headers.CONTENT_TYPE , 0);
    assertEquals("application/json; charset=UTF-8;", headerValue);
  }
  
  @Test 
  public void doesAddCharsetIfThereIsNoCharset(){
    response.setCharset("UTF-8");
    
    System.out.println( exchange.getResponseHeaders().get( Headers.CONTENT_TYPE ));
  }
  
}