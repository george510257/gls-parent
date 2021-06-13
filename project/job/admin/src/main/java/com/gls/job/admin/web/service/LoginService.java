package com.gls.job.admin.web.service;

import com.gls.job.admin.core.i18n.I18nHelper;
import com.gls.job.admin.core.util.CookieUtil;
import com.gls.job.admin.core.util.JacksonUtil;
import com.gls.job.admin.web.dao.JobUserDao;
import com.gls.job.admin.web.model.JobUser;
import com.gls.job.core.api.model.Result;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigInteger;

/**
 * @author xuxueli 2019-05-04 22:13:264
 */
@Service
public class LoginService {

    public static final String LOGIN_IDENTITY_KEY = "GLS_JOB_LOGIN_IDENTITY";
    @Resource
    public I18nHelper i18nHelper;
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

}
