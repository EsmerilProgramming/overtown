package org.esmerilprogramming.cloverx.server.handlers;

import java.lang.reflect.Method;

/**
 * Created by efraimgentil<efraimgentil@gmail.com> on 28/01/15.
 */
public class PathMapping {

  private String path;
  private String httpMethod;
  private Method method;

  public PathMapping(String path, String httpMethod, Method method) {
    this.path = path;
    this.httpMethod = httpMethod;
    this.method = method;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    PathMapping that = (PathMapping) o;
    if (!httpMethod.equals(that.httpMethod)) return false;
    if (!method.equals(that.method)) return false;
    if (!path.equals(that.path)) return false;
    return true;
  }

  @Override
  public int hashCode() {
    int result = path.hashCode();
    result = 31 * result + httpMethod.hashCode();
    result = 31 * result + method.hashCode();
    return result;
  }

  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }

  public String getHttpMethod() {
    return httpMethod;
  }

  public void setHttpMethod(String httpMethod) {
    this.httpMethod = httpMethod;
  }

  public Method getMethod() {
    return method;
  }

  public void setMethod(Method method) {
    this.method = method;
  }
}
