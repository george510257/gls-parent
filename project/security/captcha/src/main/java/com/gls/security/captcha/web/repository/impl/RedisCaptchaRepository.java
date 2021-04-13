package com.gls.security.captcha.web.repository.impl;

import com.gls.security.captcha.constants.CaptchaProperties;
import com.gls.security.captcha.exception.CaptchaException;
import com.gls.security.captcha.web.model.CaptchaDTO;
import com.gls.security.captcha.web.repository.CaptchaRepository;
import com.gls.starter.web.support.ServletHelper;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

/**
 * @author george
 */
@AllArgsConstructor
public class RedisCaptchaRepository implements CaptchaRepository {

    private final RedisTemplate<Object, Object> redisTemplate;

    private final CaptchaProperties captchaProperties;

    @Override
    public void saveCaptcha(String type, CaptchaDTO captcha) {
        redisTemplate.opsForValue().set(buildKey(type), captcha, 30, TimeUnit.MINUTES);
    }

    @Override
    public CaptchaDTO getCaptcha(String type) {
        Object value = redisTemplate.opsForValue().get(buildKey(type));
        if (value == null) {
            return null;
        }
        return (CaptchaDTO) value;
    }

    @Override
    public void removeCaptcha(String type) {
        redisTemplate.delete(buildKey(type));
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
