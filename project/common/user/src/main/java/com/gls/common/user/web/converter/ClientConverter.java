package com.gls.common.user.web.converter;

import com.gls.common.user.api.model.ClientModel;
import com.gls.common.user.web.entity.ClientEntity;
import com.gls.framework.core.base.BaseConverter;
import com.gls.framework.core.utils.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author george
 */
@Component
public class ClientConverter extends BaseConverter<ClientEntity, ClientModel> {

    @Resource
    private RoleConverter roleConverter;

    @Override
    protected ClientModel copySourceToTarget(ClientEntity client) {
        ClientModel clientModel = new ClientModel();
        clientModel.setId(client.getId());
        clientModel.setClientId(client.getClientId());
        clientModel.setClientSecret(client.getClientSecret());
        clientModel.setScope(StringUtils.toList(client.getScope()));
        clientModel.setResourceIds(StringUtils.toList(client.getResourceIds()));
        clientModel.setAuthorizedGrantTypes(StringUtils.toList(client.getAuthorizedGrantTypes()));
        clientModel.setRegisteredRedirectUris(StringUtils.toList(client.getRegisteredRedirectUris()));
        clientModel.setAutoApproveScopes(StringUtils.toList(client.getAutoApproveScopes()));
        clientModel.setRoles(roleConverter.sourceToTarget(client.getRoles()));
        clientModel.setAccessTokenValiditySeconds(client.getAccessTokenValiditySeconds());
        clientModel.setRefreshTokenValiditySeconds(client.getRefreshTokenValiditySeconds());
        clientModel.setAdditionalInformation(StringUtils.toMap(client.getAdditionalInformation()));
        return clientModel;
    }

    @Override
    protected ClientEntity copyTargetToSource(ClientModel clientModel) {
        ClientEntity client = new ClientEntity();
        client.setClientId(clientModel.getClientId());
        client.setClientSecret(clientModel.getClientSecret());
        client.setScope(StringUtils.toString(clientModel.getScope()));
        client.setResourceIds(StringUtils.toString(clientModel.getResourceIds()));
        client.setAuthorizedGrantTypes(StringUtils.toString(clientModel.getAuthorizedGrantTypes()));
        client.setRegisteredRedirectUris(StringUtils.toString(clientModel.getRegisteredRedirectUris()));
        client.setAutoApproveScopes(StringUtils.toString(clientModel.getAutoApproveScopes()));
        client.setRoles(roleConverter.targetToSource(clientModel.getRoles()));
        client.setAccessTokenValiditySeconds(clientModel.getAccessTokenValiditySeconds());
        client.setRefreshTokenValiditySeconds(clientModel.getRefreshTokenValiditySeconds());
        client.setAdditionalInformation(StringUtils.toString(clientModel.getAdditionalInformation()));
        client.setId(clientModel.getId());
        return client;
    }

}
