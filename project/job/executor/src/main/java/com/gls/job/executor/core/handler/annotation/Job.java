package com.gls.job.executor.core.handler.annotation;

import java.lang.annotation.*;

/**
 * annotation for method jobHandler
 *
 * @author xuxueli 2019-12-11 20:50:13
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Job {
    /**
     * destroy handler, invoked when JobThread destroy
     */
    String destroy() default "";

    /**
     * init handler, invoked when JobThread init
     */
    String init() default "";

    /**
     * jobHandler name
     */
    String value();
}
