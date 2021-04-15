package com.gls.security.captcha.web.repository.impl;

import com.gls.security.captcha.constants.CaptchaProperties;
import com.gls.security.captcha.exception.CaptchaException;
import com.gls.security.captcha.web.entity.CaptchaEntity;
import com.gls.security.captcha.web.repository.CaptchaRepository;
import com.gls.starter.data.redis.support.RedisHelper;
import com.gls.starter.web.support.ServletHelper;
import lombok.AllArgsConstructor;

import javax.servlet.http.HttpServletRequest;

/**
 * @author george
 */
@AllArgsConstructor
public class RedisCaptchaRepository implements CaptchaRepository {

    private final RedisHelper redisHelper;

    private final CaptchaProperties captchaProperties;

    @Override
    public void saveCaptcha(String type, CaptchaEntity captcha) {
        redisHelper.set(buildKey(type), captcha);
    }

    @Override
    public CaptchaEntity getCaptcha(String type) {
        Object value = redisHelper.get(buildKey(type));
        if (value == null) {
            return null;
        }
        return (CaptchaEntity) value;
    }

    @Override
    public void removeCaptcha(String type) {
        redisHelper.del(buildKey(type));
    }

    private String buildKey(String type) {
        HttpServletRequest request = ServletHelper.getRequest();
        String deviceId = request.getHeader(captchaProperties.getDeviceIdParameter());
        if (deviceId == null || deviceId.isEmpty()) {
            throw new CaptchaException("请在请求头中携带deviceId参数");
        }
        return "captcha/" + type.toLowerCase() + "/" + deviceId;
    }
}
