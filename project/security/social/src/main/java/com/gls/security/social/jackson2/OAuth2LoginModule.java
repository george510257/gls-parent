package com.gls.security.social.jackson2;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.gls.security.social.jackson2.mixin.OAuth2AuthorizationResponseTypeMixin;
import org.springframework.security.jackson2.SecurityJackson2Modules;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationResponseType;

/**
 * @author george
 */
@SuppressWarnings("AlibabaClassNamingShouldBeCamel")
public class OAuth2LoginModule extends SimpleModule {

    public OAuth2LoginModule() {
        super(OAuth2LoginModule.class.getName(), new Version(1, 0, 0, null, null, null));
    }

    @Override
    public void setupModule(SetupContext context) {
        SecurityJackson2Modules.enableDefaultTyping(context.getOwner());
        context.setMixInAnnotations(OAuth2AuthorizationResponseType.class, OAuth2AuthorizationResponseTypeMixin.class);
    }
}
