package org.esmerilprogramming.cloverx.http;

import java.util.Calendar;
import java.util.Date;

import io.undertow.server.HttpServerExchange;
import io.undertow.server.session.Session;

public class CloverXSession {
  
  private HttpServerExchange exchange;
  private Session session;
  
  public void setAttribute(String attrName , Object value ){
    session.setAttribute( attrName , value );
  }
  
  public Object getAttribute(String attrName){
    return session.getAttribute(attrName);
  }
  
  @SuppressWarnings("unchecked")
  public <T> T getAttribute(String attrName, Class<T> espectedType ){
    Object value = session.getAttribute(attrName);
    if(value != null && espectedType.equals( value.getClass() ) ){
      return (T) session.getAttribute(attrName);
    }
    throw new IllegalArgumentException("Espected type is not from the same type of the attribute value");
  }
  
  public void removeAttribute(String attrName){
    session.removeAttribute(attrName);
  }
  
  public void destroy(){
    session.invalidate(exchange);
  }
  
  public String getSessionId(){
    return session.getId();
  }
  
  public Date getCreationTime(){
    return new Date( session.getCreationTime() );
  }
  
  public Date getLastAccessTime(){
    return new Date( session.getLastAccessedTime() );
  }
  
  public Integer getMaxInactiveInterval(){
    return session.getMaxInactiveInterval();
  }
  
  
  
  
}
