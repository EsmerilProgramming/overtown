package org.esmerilprogramming.cloverx.annotation.path;


import java.lang.annotation.*;

/**
 * Created by efraimgentil<efraimgentil@gmail.com> on 28/01/15.
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Get {

  String template()  default Path.NO_TEMPLATE;

  String[] value() default Path.NO_PATH;

}
