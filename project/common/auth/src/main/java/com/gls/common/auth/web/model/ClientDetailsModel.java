package com.gls.common.auth.web.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gls.common.user.api.model.ClientModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author george
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientDetailsModel implements ClientDetails {

    private ClientModel clientModel;

    @JsonIgnore
    @Override
    public String getClientId() {
        return clientModel.getClientId();
    }

    @JsonIgnore
    @Override
    public Set<String> getResourceIds() {
        return new HashSet<>(clientModel.getResourceIds());
    }

    @JsonIgnore
    @Override
    public boolean isSecretRequired() {
        return clientModel.getClientSecret() != null;
    }

    @JsonIgnore
    @Override
    public String getClientSecret() {
        return clientModel.getClientSecret();
    }

    @JsonIgnore
    @Override
    public boolean isScoped() {
        return clientModel.getScope() != null && !clientModel.getScope().isEmpty();
    }

    @JsonIgnore
    @Override
    public Set<String> getScope() {
        return new HashSet<>(clientModel.getScope());
    }

    @JsonIgnore
    @Override
    public Set<String> getAuthorizedGrantTypes() {
        return new HashSet<>(clientModel.getAuthorizedGrantTypes());
    }

    @JsonIgnore
    @Override
    public Set<String> getRegisteredRedirectUri() {
        return new HashSet<>(clientModel.getRegisteredRedirectUris());
    }

    @JsonIgnore
    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return clientModel.getRoles().stream().map(GrantedAuthorityModel::new).collect(Collectors.toList());
    }

    @JsonIgnore
    @Override
    public Integer getAccessTokenValiditySeconds() {
        return clientModel.getAccessTokenValiditySeconds();
    }

    @JsonIgnore
    @Override
    public Integer getRefreshTokenValiditySeconds() {
        return clientModel.getRefreshTokenValiditySeconds();
    }

    @JsonIgnore
    @Override
    public boolean isAutoApprove(String scope) {
        List<String> autoApproveScopes = clientModel.getAutoApproveScopes();
        if (autoApproveScopes == null) {
            return false;
        }
        for (String auto : autoApproveScopes) {
            if ("true".equals(auto) || scope.matches(auto)) {
                return true;
            }
        }
        return false;
    }

    @JsonIgnore
    @Override
    public Map<String, Object> getAdditionalInformation() {
        return clientModel.getAdditionalInformation();
    }
}
