package com.gls.job.admin.web.controller.helper;

import cn.hutool.extra.servlet.ServletUtil;
import com.gls.framework.core.util.JsonUtil;
import com.gls.job.admin.web.model.JobUser;
import org.springframework.stereotype.Component;
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
@Component
public class LoginUserHelper {

    private static final String LOGIN_IDENTITY_KEY = "GLS_JOB_LOGIN_IDENTITY";

    public void saveLoginUser(JobUser jobUser, boolean ifRemember) {
        getRequest().setAttribute(LOGIN_IDENTITY_KEY, jobUser);
        String loginToken = makeToken(jobUser);
        setCookie(LOGIN_IDENTITY_KEY, loginToken, ifRemember);
    }

    public JobUser getLoginUser() {
        JobUser jobUser = (JobUser) getRequest().getAttribute(LOGIN_IDENTITY_KEY);
        if (!ObjectUtils.isEmpty(jobUser)) {
            return jobUser;
        }
        String cookieToken = getCookieValue(LOGIN_IDENTITY_KEY);
        jobUser = parseToken(cookieToken);
        return jobUser;
    }

    public void removeLoginUser() {
        removeCookie(LOGIN_IDENTITY_KEY);
    }

    public HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
    }

    public HttpServletResponse getResponse() {
        return ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getResponse();
    }

    public void setCookie(String key, String value, boolean ifRemember) {
        int age = ifRemember ? Integer.MAX_VALUE : -1;
        ServletUtil.addCookie(getResponse(), key, value, age);
    }

    public String getCookieValue(String key) {
        Cookie cookie = ServletUtil.getCookie(getRequest(), key);
        if (cookie != null) {
            return cookie.getValue();
        }
        return null;
    }

    public void removeCookie(String key) {
        Cookie cookie = ServletUtil.getCookie(getRequest(), key);
        if (cookie != null) {
            ServletUtil.addCookie(getResponse(), key, "", 0);
        }
    }

    private String makeToken(JobUser jobUser) {
        String tokenJson = JsonUtil.writeValueAsString(jobUser);
        return new BigInteger(tokenJson.getBytes()).toString(16);
    }

    private JobUser parseToken(String cookieToken) {
        if (StringUtils.hasText(cookieToken)) {
            String tokenJson = new String(new BigInteger(cookieToken, 16).toByteArray());
            return JsonUtil.readValue(tokenJson, JobUser.class);
        }
        return null;
    }

    public boolean validPermission(Long jobGroupId) {
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
