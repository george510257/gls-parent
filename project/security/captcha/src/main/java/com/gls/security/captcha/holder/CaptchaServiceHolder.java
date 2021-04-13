package com.gls.security.captcha.holder;

import com.gls.security.captcha.exception.CaptchaException;
import com.gls.security.captcha.web.model.CaptchaDTO;
import com.gls.security.captcha.web.service.CaptchaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 校验码处理器管理器
 *
 * @author george
 */
@Component
@Slf4j
public class CaptchaServiceHolder {

    @Resource
    private Map<String, CaptchaService<? extends CaptchaDTO>> captchaServices;

    public CaptchaService<? extends CaptchaDTO> findCaptchaService(String type) {
        String key = type + CaptchaService.class.getSimpleName();
        log.info("key: {}", key);
        CaptchaService<? extends CaptchaDTO> captchaService = captchaServices.get(key);
        if (captchaService == null) {
            throw new CaptchaException("验证码处理器" + key + "不存在");
        }
        return captchaService;
    }

    public Map<String, CaptchaService<? extends CaptchaDTO>> getAllCaptchaServices() {
        return captchaServices;
    }
}
