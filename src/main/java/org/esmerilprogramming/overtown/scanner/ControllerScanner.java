package org.esmerilprogramming.overtown.scanner;

import org.esmerilprogramming.overtown.annotation.BeforeTranslate;
import org.esmerilprogramming.overtown.annotation.Controller;
import org.esmerilprogramming.overtown.annotation.Page;
import org.esmerilprogramming.overtown.annotation.path.*;
import org.esmerilprogramming.overtown.server.handlers.ControllerMapping;
import org.reflections.ReflectionUtils;

/**
 * Created by efraimgentil<efraimgentil@gmail.com> on 28/01/15.
 */
public class ControllerScanner {

  private final String NO_PATH = "$$NO_PATH$$";

  public ControllerMapping scanControllerForMapping(Class<?> controllerClass){

    Controller annotation = controllerClass.getAnnotation(Controller.class);
    ControllerMapping mapping = new ControllerMapping( getControllerSimpleName(controllerClass) ,  getPath( annotation , controllerClass ) );
    mapping.setControllerClass(controllerClass);
    mapping.addPathMethods( ReflectionUtils.getAllMethods(controllerClass, ReflectionUtils.withAnnotation(Page.class) )  );
    mapping.addPathMethods( ReflectionUtils.getAllMethods(controllerClass, ReflectionUtils.withAnnotation(Get.class))  );
    mapping.addPathMethods( ReflectionUtils.getAllMethods(controllerClass, ReflectionUtils.withAnnotation(Post.class)) );
    mapping.addPathMethods( ReflectionUtils.getAllMethods(controllerClass, ReflectionUtils.withAnnotation(Put.class)) );
    mapping.addPathMethods( ReflectionUtils.getAllMethods(controllerClass, ReflectionUtils.withAnnotation(Delete.class)) );
    mapping.addPathMethods( ReflectionUtils.getAllMethods(controllerClass, ReflectionUtils.withAnnotation(Path.class)) );
    mapping.addBeforeTranslationMethods(  ReflectionUtils.getAllMethods(controllerClass, ReflectionUtils.withAnnotation(BeforeTranslate.class)) );

    return mapping;
  }

  public String getPath( Controller annotation , Class<?> controllerClass ){
    String path = annotation.path();
    StringBuilder pathBuilder = new StringBuilder(path);
    if(NO_PATH.equalsIgnoreCase( pathBuilder.toString() )){
      path = getControllerSimpleName( controllerClass );
    }
    return path;
  }

  public String getControllerSimpleName( Class<?> controllerClass ){
    StringBuilder sb = new StringBuilder( controllerClass.getSimpleName() );
    String simpleName = controllerClass.getSimpleName();
    if( simpleName.matches(".{1,}Controller") ){
      sb.reverse().replace( 0 , 10 , "" ).reverse().toString();
    }
    return sb.replace(0 , 1 , ((Character) sb.charAt(0) ).toString().toLowerCase() ).toString();
  }

}
