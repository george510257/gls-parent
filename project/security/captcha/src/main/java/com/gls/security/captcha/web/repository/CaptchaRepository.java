package com.gls.security.captcha.web.repository;

import com.gls.security.captcha.web.model.CaptchaDTO;

/**
 * @author george
 */
public interface CaptchaRepository {

    /**
     * 保存验证码
     *
     * @param type
     * @param captcha
     */
    void saveCaptcha(String type, CaptchaDTO captcha);

    /**
     * 获取验证码
     *
     * @param type
     * @return
     */
    CaptchaDTO getCaptcha(String type);

    /**
     * 移除验证码
     *
     * @param type
     */
    void removeCaptcha(String type);
}
