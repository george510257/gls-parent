package com.gls.quality.inspection.process.web.service.impl;

import com.gls.quality.inspection.process.web.converter.UserConverter;
import com.gls.quality.inspection.process.web.entity.UserEntity;
import com.gls.quality.inspection.process.web.model.UserModel;
import com.gls.quality.inspection.process.web.model.query.QueryUserModel;
import com.gls.quality.inspection.process.web.repository.UserRepository;
import com.gls.quality.inspection.process.web.service.UserService;
import com.gls.starter.data.jpa.base.BaseServiceImpl;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

/**
 * @author george
 */
@Service
public class UserServiceImpl
        extends BaseServiceImpl<UserRepository, UserConverter, UserEntity, UserModel, QueryUserModel>
        implements UserService {
    public UserServiceImpl(UserRepository repository, UserConverter converter) {
        super(repository, converter);
    }

    @Override
    protected Specification<UserEntity> getSpec(QueryUserModel queryUserModel) {
        return null;
    }
}
