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
              pathMappings.add(new PathMapping(path, vap.httpVerb, m));
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
      return new VerbAndPaths[]{ new VerbAndPaths( HttpMethod.GET , get.value() ) };
    }
    if(annotationClass.equals(Post.class) ){
      Post post = (Post) annotation;
      return new VerbAndPaths[]{ new VerbAndPaths( HttpMethod.POST , post.value() ) };
    }
    if(annotationClass.equals(Path.class)){
      Path path = (Path) annotation;
      return new VerbAndPaths[]{
              new VerbAndPaths( HttpMethod.GET , path.value() )
              , new VerbAndPaths( HttpMethod.POST , path.value() )
      };
    }
    if(annotationClass.equals(Page.class)){
      Page page = (Page) annotation;
      return new VerbAndPaths[]{
              new VerbAndPaths( HttpMethod.GET , page.value() )
              , new VerbAndPaths( HttpMethod.POST , page.value() )
      };
    }
    throw new NotAPathAnnotationException();
  }

  private class VerbAndPaths{
    private String httpVerb;
    private String[] paths;
    VerbAndPaths(String verb , String[] paths){
      this.httpVerb = verb;
      this.paths = paths;
    }
  }

  public void addBeforeTranslationMethods(Set<Method> methods){
    beforeTranslationMethods.addAll(methods);
  }

  public String getPath() {
    return path;
  }
  public Set<PathMapping> getPathMappings(){
    return pathMappings;
  }


}
