package org.esmerilprogramming.cloverx.http;

import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;
import io.undertow.util.HttpString;

import java.nio.ByteBuffer;

import org.esmerilprogramming.cloverx.view.ViewAttributes;


public abstract class Response {

  protected boolean responseSend = false;
  protected HttpServerExchange exchange;
  protected ViewAttributes viewAttributes;

  public Response(HttpServerExchange exchange) {
    this.exchange = exchange;
    this.viewAttributes = new ViewAttributes();
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
    exchange.getResponseHeaders().add(Headers.CONTENT_TYPE, "charset= " + charset);
  }

  protected void addAttribute(String name, Object value) {
    viewAttributes.add(name, value);
  }

  protected abstract void sendToView(String viewName);
  
  protected abstract void sendAsResponse(String content);

  protected abstract void sendAsResponse(ByteBuffer buffer);

  public void sendRedirect(String toPath) {
    exchange.setResponseCode(StatusSuccess.TEMPORARY_REDIRECT.getCode());
    exchange.getResponseHeaders().put(Headers.LOCATION, toPath);
    close();
  }

  public void sendError(StatusError statusError) {
    setStatus(statusError.getCode());
    close();
  }

  protected final void close() {
    if (!responseSend) {
      exchange.getResponseSender().close();
      responseSend = true;
    } else {
      throw new ResponseAlreadySendException();
    }
  }

}
