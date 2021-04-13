package com.gls.security.browser.customizer;

import com.gls.security.core.constants.SecurityProperties;
import com.gls.security.core.web.service.UserService;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.RememberMeConfigurer;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author george
 */
@Component
public class RememberMeCustomizer implements Customizer<RememberMeConfigurer<HttpSecurity>> {

    @Resource
    private PersistentTokenRepository tokenRepository;

    @Resource
    private SecurityProperties securityProperties;

    @Resource
    private UserService userService;

    @Override
    public void customize(RememberMeConfigurer<HttpSecurity> rememberMeConfigurer) {
        rememberMeConfigurer.tokenRepository(tokenRepository)
                .tokenValiditySeconds(securityProperties.getRememberMe().getTokenValiditySeconds())
                .userDetailsService(userService);
    }
}
