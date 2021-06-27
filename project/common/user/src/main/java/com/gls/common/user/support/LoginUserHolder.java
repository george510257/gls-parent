package com.gls.common.user.support;

import com.alibaba.fastjson.JSONObject;
import com.gls.common.user.api.model.UserModel;
import com.gls.common.user.web.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author george
 */
@Slf4j
@Component
public class LoginUserHolder {
    @Resource
    private UserService userService;

    public UserModel getCurrentUser() {
        //从Header中获取用户信息
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert servletRequestAttributes != null;
        HttpServletRequest request = servletRequestAttributes.getRequest();
        String userStr = request.getHeader("user");
        log.info(userStr);
        JSONObject userJsonObject = JSONObject.parseObject(userStr);
        String username = userJsonObject.getString("user_name");
        return userService.loadUserByUsername(username);
    }
}
