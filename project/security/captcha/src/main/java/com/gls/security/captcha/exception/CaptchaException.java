package com.gls.security.captcha.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * @author georg
 */
public class CaptchaException extends AuthenticationException {

    public CaptchaException(String msg, Throwable t) {
        super(msg, t);
    }

    public CaptchaException(String msg) {
        super(msg);
    }
}
