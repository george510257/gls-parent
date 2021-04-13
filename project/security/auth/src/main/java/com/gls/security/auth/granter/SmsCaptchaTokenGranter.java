package com.gls.security.auth.granter;

import com.gls.security.captcha.exception.CaptchaException;
import com.gls.security.captcha.web.service.impl.SmsCaptchaService;
import com.gls.security.core.mobile.token.MobileAuthenticationToken;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author george
 */
public class SmsCaptchaTokenGranter extends BaseCaptchaTokenGranter<SmsCaptchaService> {

    private static final String GRANT_TYPE = "sms_captcha";
    private final AuthenticationManager authenticationManager;

    public SmsCaptchaTokenGranter(SmsCaptchaService service, AuthenticationManager authenticationManager, AuthorizationServerTokenServices tokenServices, ClientDetailsService clientDetailsService, OAuth2RequestFactory requestFactory) {
        super(service, authenticationManager, tokenServices, clientDetailsService, requestFactory, GRANT_TYPE);
        this.authenticationManager = authenticationManager;
    }

    @Override
    protected OAuth2Authentication getOAuth2Authentication(ClientDetails client, TokenRequest tokenRequest) {
        try {
            service.validate();
        } catch (CaptchaException e) {
            throw new InvalidGrantException(e.getMessage());
        }
        Map<String, String> parameters = new LinkedHashMap<String, String>(tokenRequest.getRequestParameters());
        String mobile = parameters.get("mobile");
        Authentication userAuth = new MobileAuthenticationToken(mobile);
        ((AbstractAuthenticationToken) userAuth).setDetails(parameters);
        try {
            userAuth = authenticationManager.authenticate(userAuth);
        } catch (AccountStatusException | BadCredentialsException e) {
            throw new InvalidGrantException(e.getMessage());
        }
        if (userAuth == null || !userAuth.isAuthenticated()) {
            throw new InvalidGrantException("Could not authenticate user: " + mobile);
        }

        OAuth2Request storedOAuth2Request = getRequestFactory().createOAuth2Request(client, tokenRequest);
        return new OAuth2Authentication(storedOAuth2Request, userAuth);
    }
}
