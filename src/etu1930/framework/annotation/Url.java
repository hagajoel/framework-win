package etu1930.framework.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Url
 */

 @Target(ElementType.METHOD)
 @Retention(RetentionPolicy.RUNTIME)
public @interface Url {
    String value() default "";
}
