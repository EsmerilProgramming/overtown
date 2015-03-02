package org.esmerilprogramming.cloverx.annotation.path;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by efraimgentil<efraimgentil@gmail.com> on 02/03/15.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface NotFound {
}
