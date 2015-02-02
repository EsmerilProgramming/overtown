package org.esmerilprogramming.cloverx.scanner;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.websocket.server.ServerEndpoint;

import org.esmerilprogramming.cloverx.annotation.Controller;
import org.esmerilprogramming.cloverx.annotation.session.SessionListener;
import org.esmerilprogramming.cloverx.server.handlers.ControllerMapping;

/**
 * 
 * @author efraimgentil (efraim.gentil@gmail.com)
 */
public class ScannerResult {

  private List<Class<?>> serverEndpoints;
  private List<Class<?>> sessionListeners;
  private List<Class<? extends HttpServlet>> servlets;
  private List<ControllerMapping> controllerMappings;

  public ScannerResult() {
    servlets = new ArrayList<>();
    serverEndpoints = new ArrayList<>();
    sessionListeners = new ArrayList<>();
    controllerMappings = new ArrayList<>();
  }

  public void addControllerMapping(ControllerMapping controllerMapping) {
    controllerMappings.add(controllerMapping);
  }
  protected void addServerEndpointClass(Class<?> serverEndpointClass) {
    serverEndpoints.add(serverEndpointClass);
  }
  protected void addServletClass(Class<? extends HttpServlet> servletClass) {
    servlets.add(servletClass);
  }
  protected void addSessionListener(Class<? > sessionListenerClass) {
    sessionListeners.add(sessionListenerClass);
  }

  public List<ControllerMapping> getControllerMappings() { return controllerMappings; }
  public List<Class<? extends HttpServlet>> getServlets() {
    return servlets;
  }
  public List<Class<?>> getServerEndpoints() {
    return serverEndpoints;
  }
  public List<Class<?>> getSessionListeners() {
    return sessionListeners;
  }


}
