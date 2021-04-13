package com.gls.security.captcha.web.service.impl;

import com.gls.security.captcha.constants.CaptchaProperties;
import com.gls.security.captcha.support.SmsCaptchaSender;
import com.gls.security.captcha.web.model.SmsCaptchaDTO;
import com.gls.security.core.constants.SecurityProperties;
import com.gls.starter.web.support.ServletHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.Set;

/**
 * @author george
 */
@Service
public class SmsCaptchaService extends BaseCaptchaService<SmsCaptchaDTO> {

    @Resource
    private SmsCaptchaSender smsCaptchaSender;

    @Resource
    private CaptchaProperties captchaProperties;

    @Resource
    private SecurityProperties securityProperties;

    @Override
    protected SmsCaptchaDTO generateCaptcha() {
        SmsCaptchaDTO smsCaptchaDTO = new SmsCaptchaDTO();
        String code = getRandom(captchaProperties.getSms().getLength());
        smsCaptchaDTO.setCode(code);
        smsCaptchaDTO.setExpireTime(LocalDateTime.now().plusSeconds(captchaProperties.getSms().getExpireIn()));
        return smsCaptchaDTO;
    }

    @Override
    protected void sendCaptcha(SmsCaptchaDTO smsCaptchaDTO) throws IOException {
        String mobileParameter = captchaProperties.getSms().getMobileParameter();
        String mobile = ServletHelper.getRequest().getParameter(mobileParameter);
        smsCaptchaSender.send(mobile, smsCaptchaDTO.getCode());
    }

    @Override
    protected String getCodeInRequest() {
        String codeParameter = captchaProperties.getSms().getCodeParameter();
        return ServletHelper.getRequest().getParameter(codeParameter);
    }

    @Override
    protected Set<String> getUrls() {
        Set<String> urls = captchaProperties.getSms().getUrls();
        urls.add(securityProperties.getMobile().getLoginProcessingUrl());
        return urls;
    }

    private String getRandom(int length) {
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            stringBuilder.append(random.nextInt(10));
        }
        return stringBuilder.toString();
    }
}
