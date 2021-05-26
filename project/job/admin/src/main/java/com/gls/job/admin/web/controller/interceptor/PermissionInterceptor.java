package com.gls.job.admin.web.controller.interceptor;

import com.gls.job.admin.core.util.I18nUtil;
import com.gls.job.admin.web.controller.annotation.PermissionLimit;
import com.gls.job.admin.web.model.JobUser;
import com.gls.job.admin.web.service.LoginService;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.AsyncHandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 权限拦截
 *
 * @author xuxueli 2015-12-12 18:09:04
 */
@Component
public class PermissionInterceptor implements AsyncHandlerInterceptor {

    @Resource
    private LoginService loginService;

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
            JobUser loginUser = loginService.ifLogin(request, response);
            if (loginUser == null) {
                response.setStatus(302);
                response.setHeader("location", request.getContextPath() + "/toLogin");
                return false;
            }
            if (needAdminister && loginUser.getRole() != 1) {
                throw new RuntimeException(I18nUtil.getString("system_permission_limit"));
            }
            request.setAttribute(LoginService.LOGIN_IDENTITY_KEY, loginUser);
        }

        return true;
    }

}
