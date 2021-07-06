package com.gls.quality.inspection.process.web.service.impl;

import com.gls.quality.inspection.process.web.converter.UserGroupingConverter;
import com.gls.quality.inspection.process.web.entity.UserGroupingEntity;
import com.gls.quality.inspection.process.web.model.UserGroupingModel;
import com.gls.quality.inspection.process.web.model.query.QueryUserGroupingModel;
import com.gls.quality.inspection.process.web.repository.UserGroupingRepository;
import com.gls.quality.inspection.process.web.service.UserGroupingService;
import com.gls.starter.data.jpa.base.BaseServiceImpl;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

/**
 * @author george
 */
@Service
public class UserGroupingServiceImpl
        extends BaseServiceImpl<UserGroupingRepository, UserGroupingConverter, UserGroupingEntity, UserGroupingModel, QueryUserGroupingModel>
        implements UserGroupingService {
    public UserGroupingServiceImpl(UserGroupingRepository repository, UserGroupingConverter converter) {
        super(repository, converter);
    }

    @Override
    protected Specification<UserGroupingEntity> getSpec(QueryUserGroupingModel queryUserGroupingModel) {
        return null;
    }
}
