package com.gls.job.admin.web.service.impl;

import com.gls.job.admin.core.i18n.I18nHelper;
import com.gls.job.admin.core.util.CookieUtil;
import com.gls.job.admin.core.util.JacksonUtil;
import com.gls.job.admin.web.converter.JobUserConverter;
import com.gls.job.admin.web.dao.JobUserDao;
import com.gls.job.admin.web.entity.JobUserEntity;
import com.gls.job.admin.web.model.JobUser;
import com.gls.job.admin.web.repository.JobUserRepository;
import com.gls.job.admin.web.service.JobUserService;
import com.gls.job.core.api.model.Result;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

/**
 * @author george
 */
@Service
public class JobUserServiceImpl implements JobUserService {

    @Resource
    public I18nHelper i18nHelper;
    @Resource
    private JobUserRepository jobUserRepository;
    @Resource
    private JobUserConverter jobUserConverter;
    @Resource
    private JobUserDao jobUserDao;

    private String makeToken(JobUser jobUser) {
        String tokenJson = JacksonUtil.writeValueAsString(jobUser);
        String tokenHex = new BigInteger(tokenJson.getBytes()).toString(16);
        return tokenHex;
    }

    private JobUser parseToken(String tokenHex) {
        JobUser jobUser = null;
        if (tokenHex != null) {
            String tokenJson = new String(new BigInteger(tokenHex, 16).toByteArray());
            // username_password(md5)
            jobUser = JacksonUtil.readValue(tokenJson, JobUser.class);
        }
        return jobUser;
    }

    @Override
    public Result<String> login(HttpServletRequest request, HttpServletResponse response, String username, String password, boolean ifRemember) {

        // param
        if (username == null || username.trim().length() == 0 || password == null || password.trim().length() == 0) {
            return new Result<String>(500, i18nHelper.getString("login_param_empty"));
        }

        // valid passowrd
        JobUser jobUser = jobUserDao.loadByUserName(username);
        if (jobUser == null) {
            return new Result<String>(500, i18nHelper.getString("login_param_unvalid"));
        }
        String passwordMd5 = DigestUtils.md5DigestAsHex(password.getBytes());
        if (!passwordMd5.equals(jobUser.getPassword())) {
            return new Result<String>(500, i18nHelper.getString("login_param_unvalid"));
        }

        String loginToken = makeToken(jobUser);

        // do login
        CookieUtil.set(response, LOGIN_IDENTITY_KEY, loginToken, ifRemember);
        return Result.SUCCESS;
    }

    /**
     * logout
     *
     * @param request
     * @param response
     */
    @Override
    public Result<String> logout(HttpServletRequest request, HttpServletResponse response) {
        CookieUtil.remove(request, response, LOGIN_IDENTITY_KEY);
        return Result.SUCCESS;
    }

    /**
     * logout
     *
     * @param request
     * @return
     */
    @Override
    public JobUser ifLogin(HttpServletRequest request, HttpServletResponse response) {
        String cookieToken = CookieUtil.getValue(request, LOGIN_IDENTITY_KEY);
        if (cookieToken != null) {
            JobUser cookieUser = null;
            try {
                cookieUser = parseToken(cookieToken);
            } catch (Exception e) {
                logout(request, response);
            }
            if (cookieUser != null) {
                JobUser dbUser = jobUserDao.loadByUserName(cookieUser.getUsername());
                if (dbUser != null) {
                    if (cookieUser.getPassword().equals(dbUser.getPassword())) {
                        return dbUser;
                    }
                }
            }
        }
        return null;
    }

    @Override
    public Map<String, Object> getPageList(String username, int role, int start, int length) {

        Page<JobUserEntity> page = jobUserRepository.getPage(username, role, start, length);

        // package result
        Map<String, Object> maps = new HashMap<>();
        // 总记录数
        maps.put("recordsTotal", page.getTotalElements());
        // 过滤后的总记录数
        maps.put("recordsFiltered", page.getTotalElements());
        // 分页列表
        maps.put("data", jobUserConverter.sourceToTargetList(page.getContent()).stream().peek(jobUser -> jobUser.setPassword("")));
        return maps;
    }

    @Override
    public boolean addUser(JobUser jobUser) {
        JobUserEntity jobUserEntity = jobUserRepository.getByUsername(jobUser.getUsername());
        if (ObjectUtils.isEmpty(jobUserEntity)) {
            jobUserEntity = jobUserConverter.targetToSource(jobUser);
            jobUserRepository.save(jobUserEntity);
            return true;
        }
        return false;
    }

    @Override
    public void updateUser(JobUser jobUser) {
        JobUserEntity jobUserEntity = jobUserRepository.getByUsername(jobUser.getUsername());
        if (!ObjectUtils.isEmpty(jobUserEntity)) {
            jobUserEntity = jobUserConverter.copyTargetToSource(jobUser, jobUserEntity);
            jobUserRepository.save(jobUserEntity);
        }
    }
}
