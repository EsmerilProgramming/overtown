package org.esmerilprogramming.overtown.scanner;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServlet;

import org.esmerilprogramming.overtown.http.ErrorHandler;
import org.esmerilprogramming.overtown.server.handlers.ControllerMapping;

/**
 * 
 * @author efraimgentil (efraim.gentil@gmail.com)
 */
public class ScannerResult {

  private List<Class<?>> serverEndpoints;
  private List<Class<?>> sessionListeners;
  private List<Class<? extends HttpServlet>> servlets;
  private List<ControllerMapping> controllerMappings;
  private Class<? extends ErrorHandler> notFoundClass;
  private Class<? extends ErrorHandler> methodNotAllowedClass;
  private Class<? extends ErrorHandler> internalErrorClass;

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
  public Class<? extends ErrorHandler> getMethodNotAllowedClass() {
    return methodNotAllowedClass;
  }
  public void setMethodNotAllowedClass(Class<? extends ErrorHandler> methodNotAllowedClass) {
    this.methodNotAllowedClass = methodNotAllowedClass;
  }
  public Class<? extends ErrorHandler> getNotFoundClass() {
    return notFoundClass;
  }
  public void setNotFoundClass(Class<? extends ErrorHandler> notFoundClass) {
    this.notFoundClass = notFoundClass;
  }

  public Class<? extends ErrorHandler> getInternalErrorClass() {
    return internalErrorClass;
  }

  public void setInternalErrorClass(Class<? extends ErrorHandler> internalErrorClass) {
    this.internalErrorClass = internalErrorClass;
  }
}
