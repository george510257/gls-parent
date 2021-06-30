package com.gls.job.admin.core.util;

import cn.hutool.extra.servlet.ServletUtil;
import com.gls.framework.core.util.JsonUtil;
import com.gls.job.admin.web.model.JobUser;
import com.gls.starter.web.support.ServletHelper;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.servlet.http.Cookie;
import java.math.BigInteger;

/**
 * @author george
 */
public class LoginUserUtil {
    private static final String LOGIN_IDENTITY_KEY = "GLS_JOB_LOGIN_IDENTITY";

    public static String getCookieValue(String key) {
        Cookie cookie = ServletUtil.getCookie(ServletHelper.getRequest(), key);
        if (cookie != null) {
            return cookie.getValue();
        }
        return null;
    }

    public static JobUser getLoginUser() {
        JobUser jobUser = (JobUser) ServletHelper.getRequest().getAttribute(LOGIN_IDENTITY_KEY);
        if (!ObjectUtils.isEmpty(jobUser)) {
            return jobUser;
        }
        String cookieToken = getCookieValue(LOGIN_IDENTITY_KEY);
        jobUser = parseToken(cookieToken);
        return jobUser;
    }

    public static void removeCookie(String key) {
        Cookie cookie = ServletUtil.getCookie(ServletHelper.getRequest(), key);
        if (cookie != null) {
            ServletUtil.addCookie(ServletHelper.getResponse(), key, "", 0);
        }
    }

    public static void removeLoginUser() {
        removeCookie(LOGIN_IDENTITY_KEY);
    }

    public static void saveLoginUser(JobUser jobUser, boolean ifRemember) {
        ServletHelper.getRequest().setAttribute(LOGIN_IDENTITY_KEY, jobUser);
        String loginToken = makeToken(jobUser);
        setCookie(LOGIN_IDENTITY_KEY, loginToken, ifRemember);
    }

    public static void setCookie(String key, String value, boolean ifRemember) {
        int age = ifRemember ? Integer.MAX_VALUE : -1;
        ServletUtil.addCookie(ServletHelper.getResponse(), key, value, age);
    }

    public static boolean validPermission(Long jobGroupId) {
        JobUser jobUser = getLoginUser();
        if (jobUser.getRole() == 1) {
            return true;
        }
        if (StringUtils.hasText(jobUser.getPermission())) {
            for (String jobGroupIdStr : jobUser.getPermission().split(",")) {
                if (Long.parseLong(jobGroupIdStr) == jobGroupId) {
                    return true;
                }
            }
        }
        return false;
    }

    private static String makeToken(JobUser jobUser) {
        String tokenJson = JsonUtil.writeValueAsString(jobUser);
        assert tokenJson != null;
        return new BigInteger(tokenJson.getBytes()).toString(16);
    }

    private static JobUser parseToken(String cookieToken) {
        if (StringUtils.hasText(cookieToken)) {
            String tokenJson = new String(new BigInteger(cookieToken, 16).toByteArray());
            return JsonUtil.readValue(tokenJson, JobUser.class);
        }
        return null;
    }
}
