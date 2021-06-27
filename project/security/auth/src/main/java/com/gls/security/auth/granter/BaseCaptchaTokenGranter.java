package com.gls.security.auth.granter;

import com.gls.security.captcha.web.service.CaptchaService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.OAuth2RequestFactory;
import org.springframework.security.oauth2.provider.password.ResourceOwnerPasswordTokenGranter;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;

/**
 * @author george
 */
public abstract class BaseCaptchaTokenGranter<Service extends CaptchaService> extends ResourceOwnerPasswordTokenGranter {
    protected Service service;

    protected BaseCaptchaTokenGranter(Service service, AuthenticationManager authenticationManager, AuthorizationServerTokenServices tokenServices, ClientDetailsService clientDetailsService, OAuth2RequestFactory requestFactory, String grantType) {
        super(authenticationManager, tokenServices, clientDetailsService, requestFactory, grantType);
        this.service = service;
    }
}