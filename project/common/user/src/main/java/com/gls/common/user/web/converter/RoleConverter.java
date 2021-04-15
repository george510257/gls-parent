package com.gls.common.user.web.converter;

import com.gls.common.user.api.model.RoleModel;
import com.gls.common.user.web.entity.RoleEntity;
import com.gls.framework.core.base.BaseConverter;
import org.springframework.stereotype.Component;

/**
 * @author george
 */
@Component
public class RoleConverter extends BaseConverter<RoleEntity, RoleModel> {

    @Override
    protected RoleModel copySourceToTarget(RoleEntity role) {
        RoleModel roleModel = new RoleModel();
        roleModel.setId(role.getId());
        roleModel.setRole(role.getRole());
        return roleModel;
    }

    @Override
    protected RoleEntity copyTargetToSource(RoleModel roleModel) {
        RoleEntity role = new RoleEntity();
        role.setRole(roleModel.getRole());
        role.setId(roleModel.getId());
        return role;
    }
}
