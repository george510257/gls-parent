package com.gls.security.captcha.web.repository;

import com.gls.security.captcha.web.entity.CaptchaEntity;

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
    void saveCaptcha(String type, CaptchaEntity captcha);

    /**
     * 获取验证码
     *
     * @param type
     * @return
     */
    CaptchaEntity getCaptcha(String type);

    /**
     * 移除验证码
     *
     * @param type
     */
    void removeCaptcha(String type);
}
