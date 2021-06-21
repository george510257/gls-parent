package com.gls.common.user.web.converter;

import com.gls.common.user.api.model.RoleModel;
import com.gls.common.user.web.entity.RoleEntity;
import com.gls.framework.core.base.BaseConverter;
import org.springframework.stereotype.Component;

/**
 * @author george
 */
@Component
public class RoleConverter implements BaseConverter<RoleEntity, RoleModel> {

    @Override
    public RoleModel copySourceToTarget(RoleEntity roleEntity, RoleModel roleModel) {
        roleModel.setId(roleEntity.getId());
        roleModel.setRole(roleEntity.getRole());
        return roleModel;
    }

    @Override
    public RoleEntity copyTargetToSource(RoleModel roleModel, RoleEntity roleEntity) {
        roleEntity.setRole(roleModel.getRole());
        roleEntity.setId(roleModel.getId());
        return roleEntity;
    }
}
