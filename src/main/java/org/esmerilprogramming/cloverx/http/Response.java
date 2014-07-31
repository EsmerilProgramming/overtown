package org.esmerilprogramming.cloverx.http;

import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;

import java.nio.ByteBuffer;


public abstract class Response {

  protected boolean responseSend = false;
  protected HttpServerExchange exchange;
  
  public Response(HttpServerExchange exchange) { 
    this.exchange = exchange; 
  }
  
  
  
  protected void setHeader(String headerName, String value){
    
  }
  
  protected void setContentType(String contentType){
    
  }
  
  protected void setCharset(String charset){
  }
  
  protected void addAttribute(String name , Object value){
    
  }
  
  protected void sendAsResponse(String string){
    
  }
  
  protected abstract void sendAsResponse(ByteBuffer buffer);
  
  public void sendRedirect(String toPath){
    if(!responseSend){
      exchange.setResponseCode( StatusSuccess.TEMPORARY_REDIRECT.getCode() );
      exchange.getResponseHeaders().put(Headers.LOCATION , toPath );
      exchange.getResponseSender().close();
      responseSend = true;
    }else{
      throw new ResponseAlreadySendException();
    }
  }
  
  public void sendError(StatusError statusError){
    if(!responseSend){
      exchange.setResponseCode( statusError.getCode() );
//      exchange.getResponseSender().close();
      responseSend = true;
    }else{
      throw new ResponseAlreadySendException();
    }
  }

}

