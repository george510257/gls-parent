package com.gls.job.admin.web.service.impl;

import com.gls.job.admin.web.controller.helper.LoginUserHelper;
import com.gls.job.admin.web.converter.JobUserConverter;
import com.gls.job.admin.web.entity.JobUserEntity;
import com.gls.job.admin.web.model.JobUser;
import com.gls.job.admin.web.repository.JobUserRepository;
import com.gls.job.admin.web.service.JobUserService;
import com.gls.job.core.exception.JobException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author george
 */
@Service("jobUserService")
public class JobUserServiceImpl implements JobUserService {
    @Resource
    private JobUserRepository jobUserRepository;
    @Resource
    private JobUserConverter jobUserConverter;
    @Resource
    private LoginUserHelper loginUserHelper;

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
        loginUserHelper.saveLoginUser(jobUserConverter.sourceToTarget(jobUserEntity), ifRemember);
        return null;
    }

    @Override
    public String logout() {
        loginUserHelper.removeLoginUser();
        return null;
    }

    @Override
    public Map<String, Object> getPageList(String username, int role, int start, int length) {
        Page<JobUserEntity> page = jobUserRepository.getPage(username, role, start, length);
        List<JobUser> jobUsers = jobUserConverter.sourceToTargetList(page.getContent());
        Map<String, Object> map = new HashMap<>(3);
        // 总记录数
        map.put("recordsTotal", page.getTotalElements());
        // 过滤后的总记录数
        map.put("recordsFiltered", page.getTotalElements());
        // 分页列表
        map.put("data", jobUsers);
        return map;
    }

    @Override
    public void addUser(JobUser jobUser) {
        JobUserEntity jobUserEntity = jobUserRepository.getByUsername(jobUser.getUsername());
        if (!ObjectUtils.isEmpty(jobUserEntity)) {
            throw new JobException("账号重复");
        }
        jobUserEntity = jobUserConverter.targetToSource(jobUser);
        jobUserRepository.save(jobUserEntity);
    }

    @Override
    public void updateUser(JobUser jobUser) {
        JobUser loginUser = loginUserHelper.getLoginUser();
        if (loginUser.getUsername().equals(jobUser.getUsername())) {
            throw new JobException("禁止操作当前登录账号");
        }
        JobUserEntity jobUserEntity = jobUserRepository.getOne(jobUser.getId());
        if (!ObjectUtils.isEmpty(jobUserEntity)) {
            jobUserEntity = jobUserConverter.copyTargetToSource(jobUser, jobUserEntity);
            jobUserRepository.save(jobUserEntity);
        }
    }

    @Override
    public void removeUser(Long id) {
        JobUser loginUser = loginUserHelper.getLoginUser();
        if (loginUser.getId().equals(id)) {
            throw new JobException("禁止操作当前登录账号");
        }
        jobUserRepository.deleteById(id);
    }

    @Override
    public void changePassword(String password) {
        JobUser loginUser = loginUserHelper.getLoginUser();
        loginUser.setPassword(password);
        JobUserEntity jobUserEntity = jobUserRepository.getOne(loginUser.getId());
        if (!ObjectUtils.isEmpty(jobUserEntity)) {
            jobUserEntity = jobUserConverter.copyTargetToSource(loginUser, jobUserEntity);
            jobUserRepository.save(jobUserEntity);
        }
    }
}
