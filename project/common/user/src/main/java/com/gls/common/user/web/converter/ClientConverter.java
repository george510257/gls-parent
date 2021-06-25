package com.gls.common.user.web.converter;

import com.gls.common.user.api.model.ClientModel;
import com.gls.common.user.web.entity.ClientEntity;
import com.gls.framework.core.base.BaseConverter;
import com.gls.framework.core.utils.CollectionUtils;
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
        clientModel.setId(clientEntity.getId());
        clientModel.setClientId(clientEntity.getClientId());
        clientModel.setClientSecret(clientEntity.getClientSecret());
        clientModel.setScope(CollectionUtils.toList(clientEntity.getScope()));
        clientModel.setResourceIds(CollectionUtils.toList(clientEntity.getResourceIds()));
        clientModel.setAuthorizedGrantTypes(CollectionUtils.toList(clientEntity.getAuthorizedGrantTypes()));
        clientModel.setRegisteredRedirectUris(CollectionUtils.toList(clientEntity.getRegisteredRedirectUris()));
        clientModel.setAutoApproveScopes(CollectionUtils.toList(clientEntity.getAutoApproveScopes()));
        clientModel.setRoles(roleConverter.sourceToTargetList(clientEntity.getRoles()));
        clientModel.setAccessTokenValiditySeconds(clientEntity.getAccessTokenValiditySeconds());
        clientModel.setRefreshTokenValiditySeconds(clientEntity.getRefreshTokenValiditySeconds());
        clientModel.setAdditionalInformation(CollectionUtils.toMap(clientEntity.getAdditionalInformation()));
        return clientModel;
    }

    @Override
    public ClientEntity copyTargetToSource(ClientModel clientModel, ClientEntity clientEntity) {
        clientEntity.setClientId(clientModel.getClientId());
        clientEntity.setClientSecret(clientModel.getClientSecret());
        clientEntity.setScope(CollectionUtils.toString(clientModel.getScope()));
        clientEntity.setResourceIds(CollectionUtils.toString(clientModel.getResourceIds()));
        clientEntity.setAuthorizedGrantTypes(CollectionUtils.toString(clientModel.getAuthorizedGrantTypes()));
        clientEntity.setRegisteredRedirectUris(CollectionUtils.toString(clientModel.getRegisteredRedirectUris()));
        clientEntity.setAutoApproveScopes(CollectionUtils.toString(clientModel.getAutoApproveScopes()));
        clientEntity.setRoles(roleConverter.targetToSourceList(clientModel.getRoles()));
        clientEntity.setAccessTokenValiditySeconds(clientModel.getAccessTokenValiditySeconds());
        clientEntity.setRefreshTokenValiditySeconds(clientModel.getRefreshTokenValiditySeconds());
        clientEntity.setAdditionalInformation(CollectionUtils.toString(clientModel.getAdditionalInformation()));
        clientEntity.setId(clientModel.getId());
        return clientEntity;
    }
}
