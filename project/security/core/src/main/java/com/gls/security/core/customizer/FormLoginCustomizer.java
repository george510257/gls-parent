package com.gls.security.core.customizer;

import com.gls.security.core.constants.SecurityProperties;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author george
 */
@Component
public class FormLoginCustomizer implements Customizer<FormLoginConfigurer<HttpSecurity>> {
    @Resource
    private AuthenticationSuccessHandler successHandler;
    @Resource
    private AuthenticationFailureHandler failureHandler;
    @Resource
    private SecurityProperties securityProperties;

    @Override
    public void customize(FormLoginConfigurer<HttpSecurity> formLoginConfigurer) {
        SecurityProperties.FormLogin formLogin = securityProperties.getFormLogin();
        formLoginConfigurer.loginPage(formLogin.getLoginPage()).permitAll()
                .loginProcessingUrl(formLogin.getLoginProcessingUrl()).permitAll()
                .successHandler(successHandler)
                .failureHandler(failureHandler);
    }
}
