package org.esmerilprogramming.cloverx.scanner;

import org.esmerilprogramming.cloverx.annotation.BeforeTranslate;
import org.esmerilprogramming.cloverx.annotation.path.Get;
import org.esmerilprogramming.cloverx.annotation.path.Post;
import org.reflections.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by efraimgentil<efraimgentil@gmail.com> on 28/01/15.
 */
public class ControllerScanner {

  public void scanControllerForMapping(Class<?> controllerClass){

    Set<Method> beforeTranslateMethods = new LinkedHashSet<>();
    Set<Method> getMethods = new LinkedHashSet<>();
    Set<Method> postMethods = new LinkedHashSet<>();
    Set<Method> putMethods = new LinkedHashSet<>();
    Set<Method> deleteMethods = new LinkedHashSet<>();

    getMethods.addAll( ReflectionUtils.getAllMethods(controllerClass, ReflectionUtils.withAnnotation(BeforeTranslate.class)));
    getMethods.addAll( ReflectionUtils.getAllMethods(controllerClass, ReflectionUtils.withAnnotation(Get.class)));
    postMethods.addAll( ReflectionUtils.getAllMethods(controllerClass, ReflectionUtils.withAnnotation(Post.class) ) );
    //putMethods.addAll( ReflectionUtils.getAllMethods(controllerClass, ReflectionUtils.withAnnotation(Get.class) ) );
    //deleteMethods.addAll( ReflectionUtils.getAllMethods(controllerClass, ReflectionUtils.withAnnotation(Get.class) ) );
  }

}
