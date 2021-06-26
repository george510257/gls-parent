package com.gls.common.user.web.converter;

import com.gls.common.user.api.model.ClientModel;
import com.gls.common.user.web.entity.ClientEntity;
import com.gls.framework.core.base.BaseConverter;
import com.gls.framework.core.util.JsonUtil;
import com.gls.framework.core.util.StringUtil;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;

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
        clientModel.setScope(StringUtil.toList(clientEntity.getScope()));
        clientModel.setResourceIds(StringUtil.toList(clientEntity.getResourceIds()));
        clientModel.setAuthorizedGrantTypes(StringUtil.toList(clientEntity.getAuthorizedGrantTypes()));
        clientModel.setRegisteredRedirectUris(StringUtil.toList(clientEntity.getRegisteredRedirectUris()));
        clientModel.setAutoApproveScopes(StringUtil.toList(clientEntity.getAutoApproveScopes()));
        clientModel.setRoles(roleConverter.sourceToTargetList(clientEntity.getRoles()));
        clientModel.setAccessTokenValiditySeconds(clientEntity.getAccessTokenValiditySeconds());
        clientModel.setRefreshTokenValiditySeconds(clientEntity.getRefreshTokenValiditySeconds());
        clientModel.setAdditionalInformation(JsonUtil.readValue(clientEntity.getAdditionalInformation(), Map.class, String.class, Object.class));
        return clientModel;
    }

    @Override
    public ClientEntity copyTargetToSource(ClientModel clientModel, ClientEntity clientEntity) {
        clientEntity.setClientId(clientModel.getClientId());
        clientEntity.setClientSecret(clientModel.getClientSecret());
        clientEntity.setScope(StringUtil.toString(clientModel.getScope()));
        clientEntity.setResourceIds(StringUtil.toString(clientModel.getResourceIds()));
        clientEntity.setAuthorizedGrantTypes(StringUtil.toString(clientModel.getAuthorizedGrantTypes()));
        clientEntity.setRegisteredRedirectUris(StringUtil.toString(clientModel.getRegisteredRedirectUris()));
        clientEntity.setAutoApproveScopes(StringUtil.toString(clientModel.getAutoApproveScopes()));
        clientEntity.setRoles(roleConverter.targetToSourceList(clientModel.getRoles()));
        clientEntity.setAccessTokenValiditySeconds(clientModel.getAccessTokenValiditySeconds());
        clientEntity.setRefreshTokenValiditySeconds(clientModel.getRefreshTokenValiditySeconds());
        clientEntity.setAdditionalInformation(JsonUtil.writeValueAsString(clientModel.getAdditionalInformation()));
        clientEntity.setId(clientModel.getId());
        return clientEntity;
    }
}
