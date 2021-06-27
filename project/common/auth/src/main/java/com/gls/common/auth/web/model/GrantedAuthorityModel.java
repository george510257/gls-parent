package com.gls.common.auth.web.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gls.common.user.api.model.RoleModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

/**
 * @author george
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GrantedAuthorityModel implements GrantedAuthority {
    private RoleModel roleModel;

    @JsonIgnore
    @Override
    public String getAuthority() {
        return roleModel.getRole();
    }
}
