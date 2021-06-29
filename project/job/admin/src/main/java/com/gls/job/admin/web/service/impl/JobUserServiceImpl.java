package com.gls.job.admin.web.service.impl;

import com.gls.framework.core.exception.GlsException;
import com.gls.job.admin.core.util.LoginUserUtil;
import com.gls.job.admin.web.converter.JobUserConverter;
import com.gls.job.admin.web.entity.JobUserEntity;
import com.gls.job.admin.web.model.JobUser;
import com.gls.job.admin.web.model.query.QueryJobUser;
import com.gls.job.admin.web.repository.JobUserRepository;
import com.gls.job.admin.web.service.JobUserService;
import com.gls.starter.data.jpa.base.BaseServiceImpl;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author george
 */
@Service("jobUserService")
public class JobUserServiceImpl
        extends BaseServiceImpl<JobUserRepository, JobUserConverter, JobUserEntity, JobUser, QueryJobUser>
        implements JobUserService {
    public JobUserServiceImpl(JobUserRepository repository, JobUserConverter converter) {
        super(repository, converter);
    }

    @Override
    public void login(String username, String password, boolean ifRemember) {
        if (!StringUtils.hasText(username) || !StringUtils.hasText(password)) {
            throw new GlsException("账号或密码为空");
        }
        JobUserEntity jobUserEntity = repository.getByUsername(username);
        if (ObjectUtils.isEmpty(jobUserEntity)) {
            throw new GlsException("账号或密码错误");
        }
        String passwordMd5 = DigestUtils.md5DigestAsHex(password.getBytes());
        if (!passwordMd5.equals(jobUserEntity.getPassword())) {
            throw new GlsException("账号或密码错误");
        }
        LoginUserUtil.saveLoginUser(converter.sourceToTarget(jobUserEntity), ifRemember);
    }

    @Override
    public void logout() {
        LoginUserUtil.removeLoginUser();
    }

    @Override
    public void changePassword(String password) {
        if (ObjectUtils.isEmpty(password)) {
            throw new GlsException("密码不可为空");
        }
        if (password.length() > 20 || password.length() < 4) {
            throw new GlsException("长度限制[4-20]");
        }
        JobUser loginUser = LoginUserUtil.getLoginUser();
        loginUser.setPassword(password);
        super.update(loginUser);
    }

    @Override
    public void add(JobUser model) {
        JobUserEntity jobUserEntity = repository.getByUsername(model.getUsername());
        if (!ObjectUtils.isEmpty(jobUserEntity)) {
            throw new GlsException("账号重复");
        }
        super.add(model);
    }

    @Override
    public void update(JobUser model) {
        JobUser loginUser = LoginUserUtil.getLoginUser();
        if (loginUser.getUsername().equals(model.getUsername())) {
            throw new GlsException("禁止操作当前登录账号");
        }
        super.update(model);
    }

    @Override
    public void remove(Long id) {
        JobUser loginUser = LoginUserUtil.getLoginUser();
        if (loginUser.getId().equals(id)) {
            throw new GlsException("禁止操作当前登录账号");
        }
        super.remove(id);
    }

    @Override
    protected Specification<JobUserEntity> getSpec(QueryJobUser queryJobUser) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.hasText(queryJobUser.getUsername())) {
                predicates.add(criteriaBuilder.like(root.get("username"), "%" + queryJobUser.getUsername() + "%"));
            }
            if (!ObjectUtils.isEmpty(queryJobUser.getRole())) {
                predicates.add(criteriaBuilder.equal(root.get("role"), queryJobUser.getRole()));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[2]));
        };
    }
}
