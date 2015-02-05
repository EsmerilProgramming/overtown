package org.esmerilprogramming.cloverx.annotation.path;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by efraimgentil<efraimgentil@gmail.com> on 28/01/15.
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Post {

  String template() default "";

  String[] value() default Path.NO_PATH;
}
