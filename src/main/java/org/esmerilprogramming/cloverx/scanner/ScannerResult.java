package org.esmerilprogramming.cloverx.scanner;

import io.undertow.server.HttpHandler;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.websocket.server.ServerEndpoint;

import org.esmerilprogramming.cloverx.annotation.Controller;

/**
 * 
 * @author efraimgentil (efraim.gentil@gmail.com)
 */
public class ScannerResult {

  private List<Class<?>> handlers;
  private List<Class<?>> serverEndpoints;
  private List<Class<? extends HttpServlet>> servlets;

  public ScannerResult() {
    handlers = new ArrayList<>();
    servlets = new ArrayList<>();
    serverEndpoints = new ArrayList<>();
  }

  @SuppressWarnings("unchecked")
  public void addClass(Class<?> unkownClass) {

    Controller annotation = unkownClass.getAnnotation(Controller.class);
    if (annotation != null)
      addHandlerClass( unkownClass);
    if (HttpServlet.class.isAssignableFrom(unkownClass))
      addServletClass((Class<? extends HttpServlet>) unkownClass);
    if( unkownClass.getAnnotation(ServerEndpoint.class) != null)
      addServerEndpointClass(unkownClass);
    
  }

  protected void addHandlerClass(Class<?> handlerClass) {
    handlers.add(handlerClass);
  }
  
  protected void addServerEndpointClass(Class<?> serverEndpointClass) {
    serverEndpoints.add(serverEndpointClass);
  }

  protected void addServletClass(Class<? extends HttpServlet> servletClass) {
    servlets.add(servletClass);
  }

  public List<Class<? extends HttpServlet>> getServlets() {
    return servlets;
  }

  public List<Class<?>> getHandlers() {
    return handlers;
  }

  public List<Class<?>> getServerEndpoints() {
    return serverEndpoints;
  }

}
