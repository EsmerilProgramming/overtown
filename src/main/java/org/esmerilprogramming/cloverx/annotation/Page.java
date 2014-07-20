package org.esmerilprogramming.cloverx.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Page {
  public static final String NO_TEMPLATE = "NO_TEMPLATE";

  String responseTemplate() default NO_TEMPLATE;

  String[] value() default "";
}
