package com.gls.starter.data.jpa.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author george
 */
@Target({TYPE, METHOD, FIELD})
@Retention(RUNTIME)
public @interface Comment {

    String value();
}