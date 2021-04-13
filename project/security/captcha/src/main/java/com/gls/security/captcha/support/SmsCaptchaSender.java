package com.gls.security.captcha.support;

/**
 * @author george
 */
public interface SmsCaptchaSender {

    /**
     * 发送短信接口
     *
     * @param mobile
     * @param code
     */
    void send(String mobile, String code);
}
