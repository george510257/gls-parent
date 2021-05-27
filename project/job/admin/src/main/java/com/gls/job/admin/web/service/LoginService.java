package com.gls.job.admin.web.service;

import com.gls.job.admin.core.util.CookieUtil;
import com.gls.job.admin.core.util.I18nUtil;
import com.gls.job.admin.core.util.JacksonUtil;
import com.gls.job.admin.web.entity.JobUserEntity;
import com.gls.job.admin.web.repository.JobUserRepository;
import com.gls.job.core.biz.model.ReturnT;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigInteger;

/**
 * @author xuxueli 2019-05-04 22:13:264
 */
@Configuration
public class LoginService {

    public static final String LOGIN_IDENTITY_KEY = "GLS_JOB_LOGIN_IDENTITY";

    @Resource
    private JobUserRepository jobUserRepository;

    private String makeToken(JobUserEntity jobUserEntity) {
        String tokenJson = JacksonUtil.writeValueAsString(jobUserEntity);
        String tokenHex = new BigInteger(tokenJson.getBytes()).toString(16);
        return tokenHex;
    }

    private JobUserEntity parseToken(String tokenHex) {
        JobUserEntity jobUserEntity = null;
        if (tokenHex != null) {
            String tokenJson = new String(new BigInteger(tokenHex, 16).toByteArray());      // username_password(md5)
            jobUserEntity = JacksonUtil.readValue(tokenJson, JobUserEntity.class);
        }
        return jobUserEntity;
    }

    public ReturnT<String> login(HttpServletRequest request, HttpServletResponse response, String username, String password, boolean ifRemember) {

        // param
        if (username == null || username.trim().length() == 0 || password == null || password.trim().length() == 0) {
            return new ReturnT<String>(500, I18nUtil.getString("login_param_empty"));
        }

        // valid passowrd
        JobUserEntity jobUserEntity = jobUserRepository.loadByUserName(username);
        if (jobUserEntity == null) {
            return new ReturnT<String>(500, I18nUtil.getString("login_param_un_valid"));
        }
        String passwordMd5 = DigestUtils.md5DigestAsHex(password.getBytes());
        if (!passwordMd5.equals(jobUserEntity.getPassword())) {
            return new ReturnT<String>(500, I18nUtil.getString("login_param_un_valid"));
        }

        String loginToken = makeToken(jobUserEntity);

        // do login
        CookieUtil.set(response, LOGIN_IDENTITY_KEY, loginToken, ifRemember);
        return ReturnT.SUCCESS;
    }

    /**
     * logout
     *
     * @param request
     * @param response
     */
    public ReturnT<String> logout(HttpServletRequest request, HttpServletResponse response) {
        CookieUtil.remove(request, response, LOGIN_IDENTITY_KEY);
        return ReturnT.SUCCESS;
    }

    /**
     * logout
     *
     * @param request
     * @return
     */
    public JobUserEntity ifLogin(HttpServletRequest request, HttpServletResponse response) {
        String cookieToken = CookieUtil.getValue(request, LOGIN_IDENTITY_KEY);
        if (cookieToken != null) {
            JobUserEntity cookieUser = null;
            try {
                cookieUser = parseToken(cookieToken);
            } catch (Exception e) {
                logout(request, response);
            }
            if (cookieUser != null) {
                JobUserEntity dbUser = jobUserRepository.loadByUserName(cookieUser.getUsername());
                if (dbUser != null) {
                    if (cookieUser.getPassword().equals(dbUser.getPassword())) {
                        return dbUser;
                    }
                }
            }
        }
        return null;
    }

}
