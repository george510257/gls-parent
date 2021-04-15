package com.gls.security.auth.granter;

import com.gls.security.captcha.exception.CaptchaException;
import com.gls.security.captcha.web.service.impl.ImagesCaptchaService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;

/**
 * @author george
 */
public class ImagesCaptchaTokenGranter extends BaseCaptchaTokenGranter<ImagesCaptchaService> {

    private static final String GRANT_TYPE = "images_captcha";

    public ImagesCaptchaTokenGranter(ImagesCaptchaService service, AuthenticationManager authenticationManager, AuthorizationServerTokenServices tokenServices, ClientDetailsService clientDetailsService, OAuth2RequestFactory requestFactory) {
        super(service, authenticationManager, tokenServices, clientDetailsService, requestFactory, GRANT_TYPE);
    }

    @SuppressWarnings("AlibabaLowerCamelCaseVariableNaming")
    @Override
    protected OAuth2Authentication getOAuth2Authentication(ClientDetails client, TokenRequest tokenRequest) {
        try {
            service.validate();
        } catch (CaptchaException e) {
            throw new InvalidGrantException(e.getMessage());
        }
        return super.getOAuth2Authentication(client, tokenRequest);
    }
}
