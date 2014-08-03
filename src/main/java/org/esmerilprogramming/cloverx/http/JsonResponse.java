package org.esmerilprogramming.cloverx.http;

import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;

import org.esmerilprogramming.cloverx.view.ViewAttributes;

public class JsonResponse extends Response {
  
  public JsonResponse(HttpServerExchange exchange, ViewAttributes viewAttributes) {
    super(exchange, viewAttributes);
    super.setContentType( "application/json; charset=UTF-8;" );
  }
  
  @Override
  public void sendRedirect(String toPath) {
    throw new IllegalStateException("Json response does not suport redirect");
  }
  
  @Override
  public void setContentType(String contentType) {
    throw new IllegalStateException("Content-type can't be changed");
  }
  
  @Override
  public void setHeader(String headerName, String value) {
    if("content-type".equalsIgnoreCase(headerName)){
      throw new IllegalStateException("Content-type can't be changed");
    }else{
      super.setHeader(headerName, value);
    }
  }
  
  @Override
  public void sendAsResponse(String jsonAsString) {
    exchange.getResponseSender().send(jsonAsString);
  }
  
  public void addObjectToJsonConnveter(String attributeName , Object conveter){
    //TODO 
    throw new IllegalStateException("ObjetToJsonConveter is not yet supported");
  }
  
  
  
  
  
}