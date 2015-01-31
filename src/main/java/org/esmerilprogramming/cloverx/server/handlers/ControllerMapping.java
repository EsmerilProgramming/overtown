package org.esmerilprogramming.cloverx.server.handlers;

import freemarker.template.utility.StringUtil;
import org.esmerilprogramming.cloverx.annotation.Page;
import org.esmerilprogramming.cloverx.annotation.path.Get;
import org.esmerilprogramming.cloverx.annotation.path.Path;
import org.esmerilprogramming.cloverx.annotation.path.Post;
import org.esmerilprogramming.cloverx.http.HttpMethod;
import org.esmerilprogramming.cloverx.server.handlers.exception.NotAPathAnnotationException;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by efraimgentil<efraimgentil@gmail.com> on 28/01/15.
 */
public class ControllerMapping {

  private Class<?> controllerClass;
  private String path;
  private Set<PathMapping> pathMappings;
  private Set<Method> beforeTranslationMethods;

  public ControllerMapping(String path){
    this.path = path;
    pathMappings = new LinkedHashSet<>();
    beforeTranslationMethods = new LinkedHashSet<>();
  }

  public void addPathMethods(Set<Method> methods){
    for( Method m : methods ){
      Annotation[] annotations = m.getAnnotations();
      for(Annotation a : annotations ) {
        VerbAndPaths[] vaps = null;
        try {
          vaps = getVerbAndPaths( a );
          for(VerbAndPaths vap : vaps) {
            for (String path : vap.paths) {
              path = path.trim().isEmpty() ? m.getName() : path.trim();
              pathMappings.add(new PathMapping(path, vap.httpVerb, m , vap.template));
            }
          }
        } catch (NotAPathAnnotationException e) {
          e.printStackTrace();
        }
      }
    }
  }

  protected VerbAndPaths[] getVerbAndPaths( Annotation annotation ) throws NotAPathAnnotationException {
    Class<? extends Annotation> annotationClass = annotation.annotationType();
    if(annotationClass.equals(Get.class) ){
      Get get = (Get) annotation;
      return new VerbAndPaths[]{ new VerbAndPaths( HttpMethod.GET , get.value() , get.template() ) };
    }
    if(annotationClass.equals(Post.class) ){
      Post post = (Post) annotation;
      return new VerbAndPaths[]{ new VerbAndPaths( HttpMethod.POST , post.value()  , post.template()) };
    }
    if(annotationClass.equals(Path.class)){
      Path path = (Path) annotation;
      return new VerbAndPaths[]{
              new VerbAndPaths( HttpMethod.GET , path.value() ,  path.template() )
              , new VerbAndPaths( HttpMethod.POST , path.value() ,  path.template() )
      };
    }
    if(annotationClass.equals(Page.class)){
      Page page = (Page) annotation;
      return new VerbAndPaths[]{
              new VerbAndPaths( HttpMethod.GET , page.value() , page.responseTemplate() )
              , new VerbAndPaths( HttpMethod.POST , page.value() , page.responseTemplate() )
      };
    }
    throw new NotAPathAnnotationException();
  }

  private class VerbAndPaths{
    private String httpVerb;
    private String[] paths;
    private String template;
    VerbAndPaths(String verb , String[] paths , String template){
      this.httpVerb = verb;
      this.paths = paths;
      this.template = template;
    }
  }

  public void addBeforeTranslationMethods(Set<Method> methods){
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
  public Set<PathMapping> getPathMappings(){
    return pathMappings;
  }


}
