package com.gls.job.admin.web.controller.helper;

import com.alibaba.fastjson.JSON;
import com.gls.job.admin.core.util.CookieUtil;
import com.gls.job.admin.web.model.JobUser;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

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
        CookieUtil.set(getResponse(), LOGIN_IDENTITY_KEY, loginToken, ifRemember);
    }

    public void removeLoginUser() {
        CookieUtil.remove(getRequest(), getResponse(), LOGIN_IDENTITY_KEY);
    }

    public JobUser getLoginUser() {
        JobUser jobUser = (JobUser) getRequest().getAttribute(LOGIN_IDENTITY_KEY);
        if (!ObjectUtils.isEmpty(jobUser)) {
            return jobUser;
        }
        String cookieToken = CookieUtil.getValue(getRequest(), LOGIN_IDENTITY_KEY);
        jobUser = parseToken(cookieToken);
        return jobUser;
    }

    private HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
    }

    private HttpServletResponse getResponse() {
        return ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getResponse();
    }

    private String makeToken(JobUser jobUser) {
        String tokenJson = JSON.toJSONString(jobUser);
        return new BigInteger(tokenJson.getBytes()).toString(16);
    }

    private JobUser parseToken(String cookieToken) {
        if (StringUtils.hasText(cookieToken)) {
            String tokenJson = new String(new BigInteger(cookieToken, 16).toByteArray());
            return JSON.parseObject(tokenJson, JobUser.class);
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
