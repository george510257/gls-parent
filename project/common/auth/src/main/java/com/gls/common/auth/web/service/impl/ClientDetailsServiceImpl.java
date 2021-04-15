package com.gls.common.auth.web.service.impl;

import com.gls.common.auth.web.model.ClientDetailsModel;
import com.gls.common.auth.web.service.ClientDetailsService;
import com.gls.common.user.api.rpc.ClientApi;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Service;

/**
 * @author george
 */
@Service(value = "clientService")
public class ClientDetailsServiceImpl implements ClientDetailsService {

    @DubboReference
    private ClientApi clientApi;

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        return new ClientDetailsModel(clientApi.loadClientByClientId(clientId));
    }

}
