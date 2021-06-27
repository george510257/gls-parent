package com.gls.security.social.customizer;

import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.client.OAuth2LoginConfigurer;
import org.springframework.stereotype.Component;

/**
 * @author george
 */
@Component
public class Oauth2LoginCustomizer implements Customizer<OAuth2LoginConfigurer<HttpSecurity>> {
    @Override
    public void customize(OAuth2LoginConfigurer<HttpSecurity> oAuth2LoginConfigurer) {
    }
}
