package com.gls.security.core.web.service;

import org.springframework.security.core.Authentication;

/**
 * @author george
 */
public interface PermissionService {

    /**
     * 判断权限
     *
     * @param authentication
     * @return
     */
    boolean hasPermission(Authentication authentication);
}
