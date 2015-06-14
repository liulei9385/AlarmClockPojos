package com.clock.anno;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * USER: liulei
 * DATE: 2015/6/14
 * TIME: 19:23
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Field {
    String name() default "";

    String type();
}
