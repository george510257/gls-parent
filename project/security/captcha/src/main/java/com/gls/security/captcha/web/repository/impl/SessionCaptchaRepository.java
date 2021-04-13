package com.gls.security.captcha.web.repository.impl;

import com.gls.security.captcha.web.model.Captcha;
import com.gls.security.captcha.web.repository.CaptchaRepository;
import com.gls.starter.web.support.ServletHelper;

import java.util.Locale;

/**
 * 基于session的验证码存取器
 *
 * @author george
 */
public class SessionCaptchaRepository implements CaptchaRepository {

    private static final String SESSION_KEY_PREFIX = "SESSION_KEY_FOR_CODE_";

    @Override
    public void saveCaptcha(String type, Captcha captcha) {
        ServletHelper.getSession().setAttribute(getSessionKey(type), captcha);
    }

    @Override
    public Captcha getCaptcha(String type) {
        return (Captcha) ServletHelper.getSession().getAttribute(getSessionKey(type));
    }

    @Override
    public void removeCaptcha(String type) {
        ServletHelper.getSession().removeAttribute(getSessionKey(type));
    }

    private String getSessionKey(String type) {
        return SESSION_KEY_PREFIX + type.toUpperCase(Locale.ROOT);
    }

}
