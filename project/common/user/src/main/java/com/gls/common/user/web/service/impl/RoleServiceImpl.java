package com.gls.common.user.web.service.impl;

import com.gls.common.user.web.entity.RoleEntity;
import com.gls.common.user.web.repository.RoleRepository;
import com.gls.common.user.web.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author george
 */
@Slf4j
@Service(value = "roleService")
@Transactional(rollbackFor = Exception.class)
public class RoleServiceImpl implements RoleService {

    @Resource
    private RoleRepository roleRepository;

    @Override
    public List<RoleEntity> loadRoles(List<RoleEntity> roles) {
        return roles.stream().map(this::loadRole).collect(Collectors.toList());
    }

    private RoleEntity loadRole(RoleEntity role) {
        String s = role.getRole();
        log.info("role: " + role);
        RoleEntity info = roleRepository.getOneByRole(s);
        if (info == null) {
            log.info("role: " + role.toString());
            info = roleRepository.save(role);
        }
        return info;
    }
}
