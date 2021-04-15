package com.gls.security.app.customizer;

import com.gls.security.core.constants.SecurityConstants;
import com.gls.security.core.constants.SecurityProperties;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author george
 */
@Component
public class Oauth2ResourceServerCustomizer implements Customizer<OAuth2ResourceServerConfigurer<HttpSecurity>> {

    @Resource
    private SecurityProperties securityProperties;

    @Override
    public void customize(OAuth2ResourceServerConfigurer<HttpSecurity> oAuth2ResourceServerConfigurer) {
        String type = securityProperties.getTokenStore().getType();
        if (SecurityConstants.DEFAULT_TOKEN_STORE_TYPE.equalsIgnoreCase(type)) {
            oAuth2ResourceServerConfigurer.opaqueToken();
        } else {
            oAuth2ResourceServerConfigurer.jwt();
        }

    }
}
