package com.gls.security.captcha.web.service.impl;

import com.gls.framework.core.util.UrlUtil;
import com.gls.security.captcha.exception.CaptchaException;
import com.gls.security.captcha.web.converter.CaptchaConverter;
import com.gls.security.captcha.web.model.CaptchaModel;
import com.gls.security.captcha.web.repository.CaptchaRepository;
import com.gls.security.captcha.web.service.CaptchaService;
import com.gls.starter.web.support.ServletHelper;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Set;

/**
 * 抽象的验证码处理器
 *
 * @author george
 */
@Slf4j
public abstract class BaseCaptchaService<C extends CaptchaModel> implements CaptchaService {
    @Resource
    private CaptchaRepository captchaRepository;
    @Resource
    private CaptchaConverter captchaConverter;

    @Override
    public void create() throws Exception {
        C captcha = generateCaptcha();
        saveCaptcha(captcha);
        sendCaptcha(captcha);
    }

    @Override
    public void validate() {
        String codeInRequest = getCodeInRequest();
        CaptchaModel codeInSession = captchaConverter.sourceToTarget(captchaRepository.getCaptcha(getType()));
        if (codeInRequest == null || "".equals(codeInRequest)) {
            throw new CaptchaException("验证码的值不能为空");
        }
        if (codeInSession == null) {
            throw new CaptchaException("验证码不存在");
        }
        log.info("codeInRequest: {}", codeInRequest);
        log.info("codeInSession: {}", codeInSession.getCode());
        if (LocalDateTime.now().isAfter(codeInSession.getExpireTime())) {
            captchaRepository.removeCaptcha(getType());
            throw new CaptchaException("验证码已过期");
        }
        if (!codeInRequest.equals(codeInSession.getCode())) {
            throw new CaptchaException("验证码不匹配");
        }
    }

    @Override
    public boolean isValidate() {
        String requestUrl = ServletHelper.getRequest().getRequestURI();
        return UrlUtil.urlsContains(requestUrl, getUrls());
    }

    /**
     * 生成校验码
     *
     * @return
     */
    protected abstract C generateCaptcha();

    /**
     * 发送校验码
     *
     * @param captcha
     * @throws IOException
     */
    protected abstract void sendCaptcha(C captcha) throws IOException;

    /**
     * 获取输入的校验码
     *
     * @return
     */
    protected abstract String getCodeInRequest();

    /**
     * 获取需要拦截的url
     *
     * @return
     */
    protected abstract Set<String> getUrls();

    private void saveCaptcha(C captcha) {
        captchaRepository.saveCaptcha(getType(), captchaConverter.targetToSource(captcha));
    }

    private String getType() {
        return getClass().getSimpleName().replaceAll(CaptchaService.class.getSimpleName(), "").toLowerCase(Locale.ROOT);
    }
}
