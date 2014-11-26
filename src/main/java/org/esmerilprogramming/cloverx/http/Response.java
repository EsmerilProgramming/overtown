package org.esmerilprogramming.cloverx.http;

import io.undertow.server.HttpServerExchange;
import io.undertow.util.HeaderValues;
import io.undertow.util.Headers;
import io.undertow.util.HttpString;

import java.nio.ByteBuffer;

import org.esmerilprogramming.cloverx.view.ViewAttributes;


public abstract class Response {

  private boolean responseSend = false;
  protected HttpServerExchange exchange;
  protected ViewAttributes viewAttributes;

  public Response(HttpServerExchange exchange , ViewAttributes viewAttributes) {
    this.exchange = exchange;
    this.viewAttributes = viewAttributes;
  }

  public void setStatus(StatusError status) {
    setStatus(status.getCode());
  }

  public void setStatus(StatusSuccess status) {
    setStatus(status.getCode());
  }

  public void setStatus(int statusCode) {
    exchange.setResponseCode(statusCode);
  }

  public void setHeader(String headerName, String value) {
    exchange.getResponseHeaders().add(new HttpString(headerName), value);
  }

  public void setContentType(String contentType) {
    exchange.getResponseHeaders().add(Headers.CONTENT_TYPE, contentType);
  }

  public void setCharset(String charset) {
    HeaderValues headerValue = exchange.getResponseHeaders().get( Headers.CONTENT_TYPE );
    if(headerValue != null){
      String contentType = headerValue.getFirst();
      if(contentType.contains("charset=")){
        contentType = contentType.replaceFirst("charset=.+[;|\\s]", "charset=" + charset + ";" );
      }else{
        contentType += "; charset=" + charset + ";";
      }
      exchange.getResponseHeaders().put( Headers.CONTENT_TYPE , contentType );
    }else{
      exchange.getResponseHeaders().add(Headers.CONTENT_TYPE, "charset=" + charset); 
    }
  }
  

  public void addAttribute(String name, Object value) {
    viewAttributes.add(name, value);
  }

  protected void fowardTo(String viewName){
    throw new IllegalStateException("This method is not suported");
  }
  
  protected void sendAsResponse(String content){
    throw new IllegalStateException("This method is not suported");
  }

  protected void sendAsResponse(ByteBuffer buffer){
    throw new IllegalStateException("This method is not suported");
  }

  public void sendRedirect(String toPath) {
    exchange.setResponseCode(StatusSuccess.TEMPORARY_REDIRECT.getCode());
    exchange.getResponseHeaders().put(Headers.LOCATION, toPath);
    close();
  }

  public void sendError(StatusError statusError) {
    setStatus(statusError.getCode());
    close();
  }
  
  public void finishResponse(){
	close();
  }

  public final void close() {
    if (!responseSend) {
      exchange.getResponseSender().close();
      responseSend = true;
    } else {
      throw new ResponseAlreadySendException();
    }
  }

  public boolean isResponseSend() {
    return responseSend;
  }

}
