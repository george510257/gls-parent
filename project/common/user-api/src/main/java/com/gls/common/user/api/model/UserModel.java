package com.gls.common.user.api.model;

import com.gls.framework.api.model.BaseModel;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author george
 */
@Data
@Accessors(chain = true)
public class UserModel extends BaseModel {
    private String password;
    private String username;
    private List<RoleModel> roles;
    private Boolean accountNonExpired;
    private Boolean accountNonLocked;
    private Boolean credentialsNonExpired;
    private Boolean enabled;
}
