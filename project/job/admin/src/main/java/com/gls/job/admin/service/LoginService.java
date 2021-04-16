package com.gls.job.admin.service;

import com.gls.job.admin.core.model.XxlJobUser;
import com.gls.job.admin.core.util.CookieUtil;
import com.gls.job.admin.core.util.I18nUtil;
import com.gls.job.admin.core.util.JacksonUtil;
import com.gls.job.admin.dao.XxlJobUserDao;
import com.gls.job.core.api.model.Result;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigInteger;

/**
 * @author george 2019-05-04 22:13:264
 */
@Configuration
public class LoginService {

    public static final String LOGIN_IDENTITY_KEY = "XXL_JOB_LOGIN_IDENTITY";

    @Resource
    private XxlJobUserDao glsJobUserDao;

    private String makeToken(XxlJobUser glsJobUser) {
        String tokenJson = JacksonUtil.writeValueAsString(glsJobUser);
        String tokenHex = new BigInteger(tokenJson.getBytes()).toString(16);
        return tokenHex;
    }

    private XxlJobUser parseToken(String tokenHex) {
        XxlJobUser glsJobUser = null;
        if (tokenHex != null) {
            String tokenJson = new String(new BigInteger(tokenHex, 16).toByteArray());      // username_password(md5)
            glsJobUser = JacksonUtil.readValue(tokenJson, XxlJobUser.class);
        }
        return glsJobUser;
    }

    public Result<String> login(HttpServletRequest request, HttpServletResponse response, String username, String password, boolean ifRemember) {

        // param
        if (username == null || username.trim().length() == 0 || password == null || password.trim().length() == 0) {
            return new Result<String>(500, I18nUtil.getString("login_param_empty"));
        }

        // valid passowrd
        XxlJobUser glsJobUser = glsJobUserDao.loadByUserName(username);
        if (glsJobUser == null) {
            return new Result<String>(500, I18nUtil.getString("login_param_unvalid"));
        }
        String passwordMd5 = DigestUtils.md5DigestAsHex(password.getBytes());
        if (!passwordMd5.equals(glsJobUser.getPassword())) {
            return new Result<String>(500, I18nUtil.getString("login_param_unvalid"));
        }

        String loginToken = makeToken(glsJobUser);

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
    public XxlJobUser ifLogin(HttpServletRequest request, HttpServletResponse response) {
        String cookieToken = CookieUtil.getValue(request, LOGIN_IDENTITY_KEY);
        if (cookieToken != null) {
            XxlJobUser cookieUser = null;
            try {
                cookieUser = parseToken(cookieToken);
            } catch (Exception e) {
                logout(request, response);
            }
            if (cookieUser != null) {
                XxlJobUser dbUser = glsJobUserDao.loadByUserName(cookieUser.getUsername());
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
