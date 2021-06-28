package com.gls.job.admin.core.servlet;

import com.gls.framework.core.exception.GlsException;
import com.gls.job.admin.core.util.LoginUserUtil;
import com.gls.job.admin.web.model.JobUser;
import com.gls.job.core.constants.JobConstants;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.AsyncHandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 权限拦截
 *
 * @author xuxueli 2015-12-12 18:09:04
 */
@Component
public class PermissionInterceptor implements AsyncHandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        // if need login
        boolean needLogin = true;
        boolean needAdminister = false;
        HandlerMethod method = (HandlerMethod) handler;
        PermissionLimit permission = method.getMethodAnnotation(PermissionLimit.class);
        if (permission != null) {
            needLogin = permission.limit();
            needAdminister = permission.administer();
        }
        if (needLogin) {
            JobUser loginUser = LoginUserUtil.getLoginUser();
            if (loginUser == null) {
                response.setStatus(302);
                response.setHeader("location", request.getContextPath() + "/toLogin");
                return false;
            }
            if (needAdminister && loginUser.getRole() != 1) {
                throw new GlsException("权限拦截");
            }
            request.setAttribute(JobConstants.LOGIN_IDENTITY_KEY, loginUser);
        }
        return true;
    }
}
