package com.gls.job.admin.core.util;

import cn.hutool.extra.servlet.ServletUtil;
import com.gls.framework.core.util.JsonUtil;
import com.gls.job.admin.web.model.JobUser;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigInteger;
import java.util.Objects;

/**
 * @author george
 */
public class LoginUserUtil {
    private static final String LOGIN_IDENTITY_KEY = "GLS_JOB_LOGIN_IDENTITY";

    public static void saveLoginUser(JobUser jobUser, boolean ifRemember) {
        getRequest().setAttribute(LOGIN_IDENTITY_KEY, jobUser);
        String loginToken = makeToken(jobUser);
        setCookie(LOGIN_IDENTITY_KEY, loginToken, ifRemember);
    }

    public static JobUser getLoginUser() {
        JobUser jobUser = (JobUser) getRequest().getAttribute(LOGIN_IDENTITY_KEY);
        if (!ObjectUtils.isEmpty(jobUser)) {
            return jobUser;
        }
        String cookieToken = getCookieValue(LOGIN_IDENTITY_KEY);
        jobUser = parseToken(cookieToken);
        return jobUser;
    }

    public static void removeLoginUser() {
        removeCookie(LOGIN_IDENTITY_KEY);
    }

    public static HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
    }

    public static HttpServletResponse getResponse() {
        return ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getResponse();
    }

    public static void setCookie(String key, String value, boolean ifRemember) {
        int age = ifRemember ? Integer.MAX_VALUE : -1;
        ServletUtil.addCookie(getResponse(), key, value, age);
    }

    public static String getCookieValue(String key) {
        Cookie cookie = ServletUtil.getCookie(getRequest(), key);
        if (cookie != null) {
            return cookie.getValue();
        }
        return null;
    }

    public static void removeCookie(String key) {
        Cookie cookie = ServletUtil.getCookie(getRequest(), key);
        if (cookie != null) {
            ServletUtil.addCookie(getResponse(), key, "", 0);
        }
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
}
