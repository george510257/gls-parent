package com.gls.security.auth.web.service.impl;

import com.gls.security.auth.web.service.ClientService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;

import java.util.Collections;

/**
 * @author george
 */
@Slf4j
@AllArgsConstructor
public class ClientServiceImpl implements ClientService {
    private final PasswordEncoder passwordEncoder;

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        log.info("clientId: {}", clientId);
        String password = passwordEncoder.encode("glseven");
        log.info("password: {}", password);
        BaseClientDetails clientDetails = new BaseClientDetails(clientId, "", "read,write,all", "authorization_code,password,client_credentials,implicit,refresh_token,images_captcha,sms_captcha", "ROLE_USER");
        clientDetails.setClientSecret(password);
        clientDetails.setRegisteredRedirectUri(Collections.singleton("http://www.baidu.com"));
        return clientDetails;
    }
}
