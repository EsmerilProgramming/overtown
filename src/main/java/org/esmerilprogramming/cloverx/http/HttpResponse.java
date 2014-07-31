package org.esmerilprogramming.cloverx.http;

import freemarker.template.TemplateException;
import io.undertow.server.HttpServerExchange;

import java.io.IOException;
import java.nio.ByteBuffer;

import org.esmerilprogramming.cloverx.view.ViewParser;

public class HttpResponse extends Response {
  
  public HttpResponse(HttpServerExchange exchange) {
    super(exchange);
  }

  public void addAttribute(String name, Object value) {
    super.addAttribute(name, value);
  }
  
  @Override
  protected void sendToView(String viewName) {
    try {
      sendAsResponse( new ViewParser().parse(viewAttributes, viewName ) );
    } catch (TemplateException  | IOException e ) {
      e.printStackTrace();
      throw new RuntimeException("Sorry but an error occurred,  verify if the view exists and try again");
    }
  }
  
  @Override
  protected void sendAsResponse(String content) {
    exchange.getResponseSender().send(content);
  }

  @Override
  protected void sendAsResponse(ByteBuffer buffer) {
    exchange.getResponseSender().send( buffer );
  }

}