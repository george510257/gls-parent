package com.gls.common.user.web.service.impl;

import com.gls.common.user.api.model.RoleModel;
import com.gls.common.user.api.model.query.QueryRoleModel;
import com.gls.common.user.web.converter.RoleConverter;
import com.gls.common.user.web.entity.RoleEntity;
import com.gls.common.user.web.repository.RoleRepository;
import com.gls.common.user.web.service.RoleService;
import com.gls.starter.data.jpa.base.BaseServiceImpl;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

/**
 * @author george
 */
@Service
public class RoleServiceImpl
        extends BaseServiceImpl<RoleRepository, RoleConverter, RoleEntity, RoleModel, QueryRoleModel>
        implements RoleService {
    public RoleServiceImpl(RoleRepository repository, RoleConverter converter) {
        super(repository, converter);
    }

    @Override
    protected Specification<RoleEntity> getSpec(QueryRoleModel queryRoleModel) {
        // todo
        return null;
    }
}
