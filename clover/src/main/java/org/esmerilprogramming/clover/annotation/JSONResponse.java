package org.esmerilprogramming.clover.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target( { ElementType.TYPE , ElementType.METHOD  })
@Retention(RetentionPolicy.RUNTIME)
public @interface JSONResponse {
	
	String rootAttribute() default "root";
	
}
