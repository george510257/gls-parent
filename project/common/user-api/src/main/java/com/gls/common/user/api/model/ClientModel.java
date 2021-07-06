package com.gls.common.user.api.model;

import com.gls.framework.api.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.Map;

/**
 * @author george
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class ClientModel extends BaseModel {
    private String clientId;
    private String clientSecret;
    private List<String> scope;
    private List<String> resourceIds;
    private List<String> authorizedGrantTypes;
    private List<String> registeredRedirectUris;
    private List<String> autoApproveScopes;
    private List<RoleModel> roles;
    private Integer accessTokenValiditySeconds;
    private Integer refreshTokenValiditySeconds;
    private Map<String, Object> additionalInformation;
}
