package com.gls.common.user.web.converter;

import com.gls.common.user.api.model.ClientModel;
import com.gls.common.user.web.entity.ClientEntity;
import com.gls.framework.core.base.BaseConverter;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author george
 */
@Component
public class ClientConverter implements BaseConverter<ClientEntity, ClientModel> {
    @Resource
    private RoleConverter roleConverter;

    @Override
    public ClientModel copySourceToTarget(ClientEntity clientEntity, ClientModel clientModel) {
        clientModel.setClientId(clientEntity.getClientId());
        clientModel.setClientSecret(clientEntity.getClientSecret());
        clientModel.setScope(clientEntity.getScope());
        clientModel.setResourceIds(clientEntity.getResourceIds());
        clientModel.setAuthorizedGrantTypes(clientEntity.getAuthorizedGrantTypes());
        clientModel.setRegisteredRedirectUris(clientEntity.getRegisteredRedirectUris());
        clientModel.setAutoApproveScopes(clientEntity.getAutoApproveScopes());
        clientModel.setRoles(roleConverter.sourceToTargetList(clientEntity.getRoles()));
        clientModel.setAccessTokenValiditySeconds(clientEntity.getAccessTokenValiditySeconds());
        clientModel.setRefreshTokenValiditySeconds(clientEntity.getRefreshTokenValiditySeconds());
        clientModel.setAdditionalInformation(clientEntity.getAdditionalInformation());
        clientModel.setId(clientEntity.getId());
        return clientModel;
    }

    @Override
    public ClientEntity copyTargetToSource(ClientModel clientModel, ClientEntity clientEntity) {
        clientEntity.setClientId(clientModel.getClientId());
        clientEntity.setClientSecret(clientModel.getClientSecret());
        clientEntity.setScope(clientModel.getScope());
        clientEntity.setResourceIds(clientModel.getResourceIds());
        clientEntity.setAuthorizedGrantTypes(clientModel.getAuthorizedGrantTypes());
        clientEntity.setRegisteredRedirectUris(clientModel.getRegisteredRedirectUris());
        clientEntity.setAutoApproveScopes(clientModel.getAutoApproveScopes());
        clientEntity.setRoles(roleConverter.targetToSourceList(clientModel.getRoles()));
        clientEntity.setAccessTokenValiditySeconds(clientModel.getAccessTokenValiditySeconds());
        clientEntity.setRefreshTokenValiditySeconds(clientModel.getRefreshTokenValiditySeconds());
        clientEntity.setAdditionalInformation(clientModel.getAdditionalInformation());
        clientEntity.setId(clientModel.getId());
        return clientEntity;
    }
}