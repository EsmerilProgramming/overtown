package org.esmerilprogramming.cloverx.server.handlers;

import freemarker.template.utility.StringUtil;
import org.esmerilprogramming.cloverx.annotation.path.Get;
import org.esmerilprogramming.cloverx.annotation.path.Post;
import org.esmerilprogramming.cloverx.http.HttpMethod;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by efraimgentil<efraimgentil@gmail.com> on 28/01/15.
 */
public class ControllerMapping {

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
        VerbAndPaths vap = getVerbAndPaths(m , a);
        for (String path : vap.paths) {
          path = path.trim().isEmpty() ? m.getName() : path.trim();
          pathMappings.add(new PathMapping(path, vap.httpVerb , m));
        }
      }
    }
  }

  protected VerbAndPaths getVerbAndPaths( Method method , Annotation annotation ) {
    VerbAndPaths vap = new VerbAndPaths();
    if(annotation.annotationType().equals(Get.class) ){
      Get get = (Get) annotation;
      return new VerbAndPaths( HttpMethod.GET , get.value() );
    }
    if(annotation.annotationType().equals(Post.class ) ){
      Post post = (Post) annotation;
      return new VerbAndPaths( HttpMethod.POST , post.value() );
    }
    return vap;
  }

  private class VerbAndPaths{
    private String httpVerb;
    private String[] paths;
    VerbAndPaths(){}
    VerbAndPaths(String verb , String[] paths){
      this.httpVerb = verb;
      this.paths = paths;
    }
  }

  public void addBeforeTranslationMethods(Set<Method> methods){

  }

  public Set<PathMapping> getPathMappings(){
    return pathMappings;
  }


}
