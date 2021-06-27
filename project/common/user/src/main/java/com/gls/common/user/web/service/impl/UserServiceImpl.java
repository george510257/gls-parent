package com.gls.common.user.web.service.impl;

import com.gls.common.user.api.model.UserModel;
import com.gls.common.user.web.converter.UserConverter;
import com.gls.common.user.web.entity.UserEntity;
import com.gls.common.user.web.repository.UserRepository;
import com.gls.common.user.web.service.RoleService;
import com.gls.common.user.web.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author george
 */
@Slf4j
@Service(value = "userService")
@Transactional(rollbackFor = Exception.class)
@CacheConfig(cacheNames = "user")
public class UserServiceImpl implements UserService {
    @Resource
    private UserModel defaultUserModel;
    @Resource
    private PasswordEncoder passwordEncoder;
    @Resource
    private RoleService roleService;
    @Resource
    private UserRepository userRepository;
    @Resource
    private UserConverter userConverter;

    @Override
    public UserModel updatePassword(UserModel userModel, String newPassword) {
        UserEntity user = userRepository.getOneByUsername(userModel.getUsername());
        user.setPassword(passwordEncoder.encode(newPassword));
        user = userRepository.save(user);
        return userConverter.sourceToTarget(user);
    }

    @Override
    @Cacheable(key = "#root.methodName+'['+#username+']'")
    public UserModel loadUserByUsername(String username) {
        UserEntity user = userRepository.getOneByUsername(username);
        return userConverter.sourceToTarget(user);
    }

    @Override
    public void saveDefaultUser() {
        UserEntity user = userConverter.targetToSource(defaultUserModel);
        String username = defaultUserModel.getUsername();
        log.info("username: " + username);
        userRepository.deleteAllByUsername(username);
        userRepository.flush();
        user.setRoles(roleService.loadRoles(user.getRoles()));
        log.info("user: " + user.toString());
        userRepository.saveAndFlush(user);
    }
}
