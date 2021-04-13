package com.gls.security.captcha.filter;

import com.gls.security.captcha.holder.CaptchaServiceHolder;
import com.gls.security.core.constants.SecurityConstants;
import com.gls.security.core.permission.PermitAllHolder;
import com.gls.security.core.permission.PermitAllProvider;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author george
 */
@Component
public class CaptchaConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> implements PermitAllProvider {

    @Resource
    private CaptchaServiceHolder captchaServiceHolder;

    @Resource
    private AuthenticationFailureHandler failureHandler;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        CaptchaFilter captchaFilter = new CaptchaFilter(captchaServiceHolder, failureHandler);
        http.addFilterBefore(captchaFilter, AbstractPreAuthenticatedProcessingFilter.class);
    }

    @Override
    public void config(PermitAllHolder holder) {
        holder.addPermitAll(SecurityConstants.DEFAULT_CAPTCHA_URL_PREFIX + "/*");
    }
}
