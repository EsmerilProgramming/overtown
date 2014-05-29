package com.clover.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation signals the method that will be called before
 * translate any method parameter 
 * 
 * @author efraimgentil (efraim.gentil@gmail.com)
 */
@Target( ElementType.METHOD )
@Retention(RetentionPolicy.RUNTIME)
public @interface BeforeTranslate {

}
