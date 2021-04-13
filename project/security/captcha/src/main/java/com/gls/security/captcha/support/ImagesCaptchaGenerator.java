package com.gls.security.captcha.support;

import com.gls.security.captcha.web.model.ImagesCaptcha;

/**
 * @author george
 */
public interface ImagesCaptchaGenerator {

    /**
     * 生成校验码
     *
     * @return
     */
    ImagesCaptcha generate();
}
