package com.gls.security.captcha.web.controller;

import com.gls.security.captcha.holder.CaptchaServiceHolder;
import com.gls.security.core.constants.SecurityConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author george
 */
@Slf4j
@RestController
@RequestMapping(SecurityConstants.DEFAULT_CAPTCHA_URL_PREFIX)
public class CaptchaController {
    @Resource
    private CaptchaServiceHolder captchaServiceHolder;

    @GetMapping("/{type}")
    public void createCode(@PathVariable String type) throws Exception {
        captchaServiceHolder.findCaptchaService(type).create();
    }
}
