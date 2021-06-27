package com.gls.security.core.permission;

/**
 * @author georg
 */
public interface PermitAllProvider {
    /**
     * 配置不用校验的url地址
     *
     * @param holder
     */
    void config(PermitAllHolder holder);
}
