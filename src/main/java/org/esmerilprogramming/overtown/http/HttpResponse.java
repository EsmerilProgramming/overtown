package org.esmerilprogramming.overtown.http;

import freemarker.template.TemplateException;
import io.undertow.server.HttpServerExchange;

import java.io.IOException;
import java.nio.ByteBuffer;

import org.esmerilprogramming.overtown.server.ConfigurationHolder;
import org.esmerilprogramming.overtown.view.ViewAttributes;
import org.esmerilprogramming.overtown.view.ViewParser;

public class HttpResponse extends Response {
  
  
  public HttpResponse(HttpServerExchange exchange, ViewAttributes viewAttributes) {
    super(exchange, viewAttributes);
    super.setContentType( "text/html; charset=UTF-8;" );
  }

  /**
   * This method should not simply draw a template, it should also forward to a diferent method if necessary
   */
  @Deprecated
  @Override
  public void fowardTo(String viewName) {
    try {
      sendAsResponse( new ViewParser( ConfigurationHolder.getInstance() ).parse(viewAttributes, viewName ) );
    } catch (TemplateException  | IOException e ) {
      e.printStackTrace();
      throw new RuntimeException("Sorry but an error occurred,  verify if the view exists and try again");
    }
  }
  
  @Override
  public void sendAsResponse(String content) {
    exchange.getResponseSender().send(content);
  }

  @Override
  public void sendAsResponse(ByteBuffer buffer) {
    exchange.getResponseSender().send( buffer );
  }

}