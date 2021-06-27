package com.gls.job.admin.web.service.impl;

import com.gls.job.admin.core.util.LoginUserUtil;
import com.gls.job.admin.web.converter.JobUserConverter;
import com.gls.job.admin.web.entity.JobUserEntity;
import com.gls.job.admin.web.model.JobUser;
import com.gls.job.admin.web.model.query.QueryJobUser;
import com.gls.job.admin.web.repository.JobUserRepository;
import com.gls.job.admin.web.service.JobUserService;
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
public class JobUserServiceImpl extends JobServiceImpl<JobUserEntity, JobUser, QueryJobUser> implements JobUserService {
    private JobUserRepository jobUserRepository;
    private JobUserConverter jobUserConverter;

    public JobUserServiceImpl(JobUserRepository jobUserRepository, JobUserConverter jobUserConverter) {
        super(jobUserRepository, jobUserConverter);
        this.jobUserRepository = jobUserRepository;
        this.jobUserConverter = jobUserConverter;
    }

    @Override
    public String login(String userName, String password, boolean ifRemember) {
        if (!StringUtils.hasText(userName) || !StringUtils.hasText(password)) {
            return "账号或密码为空";
        }
        JobUserEntity jobUserEntity = jobUserRepository.getByUsername(userName);
        if (ObjectUtils.isEmpty(jobUserEntity)) {
            return "账号或密码错误";
        }
        String passwordMd5 = DigestUtils.md5DigestAsHex(password.getBytes());
        if (!passwordMd5.equals(jobUserEntity.getPassword())) {
            return "账号或密码错误";
        }
        LoginUserUtil.saveLoginUser(jobUserConverter.sourceToTarget(jobUserEntity), ifRemember);
        return null;
    }

    @Override
    public String logout() {
        LoginUserUtil.removeLoginUser();
        return null;
    }

    @Override
    public void changePassword(String password) {
        JobUser loginUser = LoginUserUtil.getLoginUser();
        loginUser.setPassword(password);
        JobUserEntity jobUserEntity = jobUserRepository.getOne(loginUser.getId());
        if (!ObjectUtils.isEmpty(jobUserEntity)) {
            jobUserEntity = jobUserConverter.copyTargetToSource(loginUser, jobUserEntity);
            jobUserRepository.save(jobUserEntity);
        }
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
