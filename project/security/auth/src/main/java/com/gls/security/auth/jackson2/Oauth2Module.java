package com.gls.security.auth.jackson2;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.gls.security.auth.jackson2.mixin.Oauth2AccessTokenMixIn;
import com.gls.security.auth.jackson2.mixin.Oauth2AuthenticationMixIn;
import com.gls.security.auth.jackson2.mixin.Oauth2RequestMixIn;
import org.springframework.security.jackson2.SecurityJackson2Modules;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;

/**
 * @author george
 */
public class Oauth2Module extends SimpleModule {

    public Oauth2Module() {
        super(Oauth2Module.class.getName(), new Version(1, 0, 0, null, null, null));
    }

    @Override
    public void setupModule(SetupContext context) {
        SecurityJackson2Modules.enableDefaultTyping(context.getOwner());
        context.setMixInAnnotations(OAuth2AccessToken.class, Oauth2AccessTokenMixIn.class);
        context.setMixInAnnotations(OAuth2Authentication.class, Oauth2AuthenticationMixIn.class);
        context.setMixInAnnotations(OAuth2Request.class, Oauth2RequestMixIn.class);
    }
}
