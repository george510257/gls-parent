package com.gls.job.admin.core.servlet;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 权限限制
 *
 * @author xuxueli 2015-12-12 18:29:02
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PermissionLimit {
    /**
     * 要求管理员权限
     *
     * @return
     */
    boolean administer() default false;

    /**
     * 登录拦截 (默认拦截)
     */
    boolean limit() default true;
}