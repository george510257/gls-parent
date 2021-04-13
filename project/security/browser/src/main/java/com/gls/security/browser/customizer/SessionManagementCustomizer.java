package com.gls.security.browser.customizer;

import com.gls.security.core.constants.SecurityProperties;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.SessionManagementConfigurer;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author george
 */
@Component
public class SessionManagementCustomizer implements Customizer<SessionManagementConfigurer<HttpSecurity>> {

    @Resource
    private InvalidSessionStrategy invalidSessionStrategy;

    @Resource
    private SessionInformationExpiredStrategy expiredSessionStrategy;

    @Resource
    private SecurityProperties securityProperties;

    @Override
    public void customize(SessionManagementConfigurer<HttpSecurity> sessionManagementConfigurer) {
        sessionManagementConfigurer
                .invalidSessionStrategy(invalidSessionStrategy)
                .maximumSessions(securityProperties.getSession().getMaximumSessions())
                .maxSessionsPreventsLogin(securityProperties.getSession().isMaxSessionsPreventsLogin())
                .expiredSessionStrategy(expiredSessionStrategy);
    }
}
