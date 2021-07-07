package com.gls.common.user.web.service.impl;

import com.gls.common.user.api.model.UserModel;
import com.gls.common.user.api.model.query.QueryUserModel;
import com.gls.common.user.web.converter.UserConverter;
import com.gls.common.user.web.entity.UserEntity;
import com.gls.common.user.web.repository.UserRepository;
import com.gls.common.user.web.service.UserService;
import com.gls.starter.data.jpa.base.BaseServiceImpl;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author george
 */
@Service
public class UserServiceImpl
        extends BaseServiceImpl<UserRepository, UserConverter, UserEntity, UserModel, QueryUserModel>
        implements UserService {
    @Resource
    private UserModel defaultUserModel;
    @Resource
    private PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository repository, UserConverter converter) {
        super(repository, converter);
    }

    @Override
    protected Specification<UserEntity> getSpec(QueryUserModel queryUserModel) {
        return null;
    }

    @Override
    public UserModel updatePassword(UserModel userModel, String newPassword) {
        UserEntity userEntity = repository.getOneByUsername(userModel.getUsername());
        userEntity.setPassword(passwordEncoder.encode(newPassword));
        repository.save(userEntity);
        return converter.sourceToTarget(userEntity);
    }

    @Override
    public UserModel loadUserByUsername(String username) {
        return converter.sourceToTarget(repository.getOneByUsername(username));
    }

    @Override
    public void saveDefaultUser() {
        UserEntity userEntity = converter.targetToSource(defaultUserModel);
        repository.deleteAllByUsername(userEntity.getUsername());
        repository.flush();
        repository.saveAndFlush(userEntity);
    }
}
