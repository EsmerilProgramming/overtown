package org.esmerilprogramming.cloverx.server.handlers;

import io.undertow.server.HttpHandler;
import org.esmerilprogramming.cloverx.annotation.JSONResponse;
import org.esmerilprogramming.cloverx.annotation.Page;
import org.esmerilprogramming.cloverx.annotation.path.*;
import org.esmerilprogramming.cloverx.http.HttpMethod;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by efraimgentil<efraimgentil@gmail.com> on 28/01/15.
 */
public class ControllerMapping {

  private Class<?> controllerClass;
  private String name;
  private String path;
  private Set<PathMapping> pathMappings;
  private Set<Method> beforeTranslationMethods;
  private HttpHandler internalErrorHandler;

  public ControllerMapping(String name , String path) {
    this.name = name;
    this.path = path;
    pathMappings = new LinkedHashSet<>();
    beforeTranslationMethods = new LinkedHashSet<>();
  }

  public void addPathMethods(Set<Method> methods) {
    for (Method m : methods) {
      Annotation[] annotations = m.getAnnotations();
      Set<VerbAndPaths> vaps = mountPathMapping(annotations);
      for (VerbAndPaths vap : vaps) {
        for (String path : vap.paths) {
          path = Path.NO_PATH.equals(path) ? m.getName() : path.trim();
          if(!Path.NO_TEMPLATE.equalsIgnoreCase( vap.template)) {
            if (!vap.template.startsWith("/")) { //Mount template with the controllerName/templateName
              vap.template = name + "/" + vap.template;
            }
          }
          pathMappings.add(new PathMapping(path, vap.httpVerb, m, vap.template, vap.jsonResponse));
        }
      }
    }
  }

  protected Set<VerbAndPaths> mountPathMapping(Annotation[] annotations) {
    Set<VerbAndPaths> verbAndPaths = new LinkedHashSet<>();
    for (Annotation annotation : annotations) {
      Class<? extends Annotation> annotationClass = annotation.annotationType();
      if (annotationClass.equals(Get.class)) {
        Get get = (Get) annotation;
        verbAndPaths.add(new VerbAndPaths(HttpMethod.GET, get.value(), get.template()));
      }
      if (annotationClass.equals(Post.class)) {
        Post post = (Post) annotation;
        verbAndPaths.add(new VerbAndPaths(HttpMethod.POST, post.value(), post.template()));
      }
      if (annotationClass.equals(Put.class)) {
        Put post = (Put) annotation;
        verbAndPaths.add(new VerbAndPaths(HttpMethod.PUT, post.value(), post.template()));
      }
      if (annotationClass.equals(Delete.class)) {
        Delete post = (Delete) annotation;
        verbAndPaths.add(new VerbAndPaths(HttpMethod.DELETE, post.value(), post.template()));
      }
      if (annotationClass.equals(Path.class)) {
        Path path = (Path) annotation;
        verbAndPaths.add(new VerbAndPaths(HttpMethod.GET, path.value(), path.template()));
        verbAndPaths.add(new VerbAndPaths(HttpMethod.POST, path.value(), path.template()));
      }
      if (annotationClass.equals(Page.class)) {
        Page page = (Page) annotation;
        verbAndPaths.add(new VerbAndPaths(HttpMethod.GET, page.value(), page.responseTemplate()));
        verbAndPaths.add(new VerbAndPaths(HttpMethod.POST, page.value(), page.responseTemplate()));
      }
    }
    verbAndPaths = checkForResponseMappings(annotations, verbAndPaths);
    return verbAndPaths;
  }

  private Set<VerbAndPaths> checkForResponseMappings(Annotation[] annotations, Set<VerbAndPaths> verbAndPaths) {
    for (Annotation annotation : annotations) {
      Class<? extends Annotation> annotationClass = annotation.annotationType();
      if (annotationClass.equals(JSONResponse.class)) {
        for (VerbAndPaths verbAndPath : verbAndPaths) {
          verbAndPath.setJsonResponse(true);
        }
      }
    }
    return verbAndPaths;
  }

  public HttpHandler getInternalErrorHandler() {
    return internalErrorHandler;
  }

  public void setInternalErrorHandler(HttpHandler internalErrorHandler) {
    this.internalErrorHandler = internalErrorHandler;
  }

  private class VerbAndPaths {
    private String httpVerb;
    private String[] paths;
    private String template;
    private boolean jsonResponse;

    VerbAndPaths(String verb, String[] paths, String template) {
      this.httpVerb = verb;
      this.paths = paths;
      this.template = template;
    }

    public void setJsonResponse(boolean jsonResponse) {
      this.jsonResponse = jsonResponse;
    }
  }

  public void addBeforeTranslationMethods(Set<Method> methods) {
    beforeTranslationMethods.addAll(methods);
  }

  public void setControllerClass(Class<?> controllerClass) {
    this.controllerClass = controllerClass;
  }

  public Class<?> getControllerClass() {
    return controllerClass;
  }

  public Set<Method> getBeforeTranslationMethods() {
    return beforeTranslationMethods;
  }

  public String getPath() {
    return path;
  }

  public Set<PathMapping> getPathMappings() {
    return pathMappings;
  }
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
}
