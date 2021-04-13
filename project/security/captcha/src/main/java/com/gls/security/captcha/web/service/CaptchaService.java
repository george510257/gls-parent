package com.gls.security.captcha.web.service;

/**
 * 校验码处理器，封装不同校验码的处理逻辑
 *
 * @author george
 */
public interface CaptchaService {

    /**
     * 创建校验码
     *
     * @throws Exception
     */
    void create() throws Exception;

    /**
     * 校验验证码
     */
    void validate();

    /**
     * 是否需要验证
     *
     * @return
     */
    boolean isValidate();

}
