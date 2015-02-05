package org.esmerilprogramming.cloverx.annotation.path;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Path {

  public static final String NO_PATH = "$$NO_PATH$$";

  String template() default "";

  String[] value() default NO_PATH;
}
