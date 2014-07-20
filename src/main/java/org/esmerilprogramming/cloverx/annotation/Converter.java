package org.esmerilprogramming.cloverx.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.esmerilprogramming.cloverx.http.converter.GenericConverter;


@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.PARAMETER })
public @interface Converter {

  Class< ? extends GenericConverter<?>> value();
  
}
