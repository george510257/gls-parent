package com.gls.security.browser.customizer;

import com.gls.security.core.constants.SecurityProperties;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author george
 */
@Component
public class LogoutCustomizer implements Customizer<LogoutConfigurer<HttpSecurity>> {

    @Resource
    private SecurityProperties securityProperties;

    @Resource
    private LogoutSuccessHandler logoutSuccessHandler;

    @Override
    public void customize(LogoutConfigurer<HttpSecurity> logoutConfigurer) {
        logoutConfigurer
                .logoutUrl(securityProperties.getLogout().getLogoutUrl())
                .logoutSuccessHandler(logoutSuccessHandler)
                .deleteCookies(securityProperties.getLogout().getDeleteCookies().toArray(new String[0]));
    }
}
