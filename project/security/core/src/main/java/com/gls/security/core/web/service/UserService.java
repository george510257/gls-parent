package com.gls.security.core.web.service;

import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @author george
 */
public interface UserService extends UserDetailsService {
    /**
     * 判断用户是否有访问url的权限
     *
     * @param requestUrl
     * @param username
     * @return
     */
    boolean hasPermission(String requestUrl, String username);
}
