package com.gls.security.captcha.web.service.impl;

import com.gls.security.captcha.constants.CaptchaProperties;
import com.gls.security.captcha.support.ImagesCaptchaGenerator;
import com.gls.security.captcha.web.model.ImagesCaptchaDTO;
import com.gls.security.core.constants.SecurityProperties;
import com.gls.starter.web.support.ServletHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Set;

/**
 * @author george
 */
@Service
public class ImagesCaptchaService extends BaseCaptchaService<ImagesCaptchaDTO> {

    @Resource
    private ImagesCaptchaGenerator imagesCaptchaGenerator;

    @Resource
    private CaptchaProperties captchaProperties;

    @Resource
    private SecurityProperties securityProperties;

    @Override
    protected ImagesCaptchaDTO generateCaptcha() {
        return imagesCaptchaGenerator.generate();
    }

    @Override
    protected void sendCaptcha(ImagesCaptchaDTO imagesCaptchaDTO) throws IOException {
        ImageIO.write(imagesCaptchaDTO.getImages(), "JPEG", ServletHelper.getResponse().getOutputStream());
    }

    @Override
    protected String getCodeInRequest() {
        String codeParameter = captchaProperties.getImages().getCodeParameter();
        return ServletHelper.getRequest().getParameter(codeParameter);
    }

    @Override
    protected Set<String> getUrls() {
        Set<String> urls = captchaProperties.getImages().getUrls();
        urls.add(securityProperties.getFormLogin().getLoginProcessingUrl());
        return urls;
    }
}
