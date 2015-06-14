package com.clock.anno;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * USER: liulei
 * DATE: 2015/6/14
 * TIME: 23:44
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Id {
    String name() default "_id";
}
