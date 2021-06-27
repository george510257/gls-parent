package com.gls.security.core.web.service.impl;

import com.gls.security.core.web.service.PermissionService;
import com.gls.security.core.web.service.UserService;
import com.gls.starter.web.support.ServletHelper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.DefaultOAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;

/**
 * @author george
 */
@Slf4j
@AllArgsConstructor
public class PermissionServiceImpl implements PermissionService {
    private final UserService userService;

    @Override
    public boolean hasPermission(Authentication authentication) {
        String requestUrl = ServletHelper.getRequest().getRequestURI();
        Object principal = authentication.getPrincipal();
        String username = getUsername(principal);
        return userService.hasPermission(requestUrl, username);
    }

    private String getUsername(Object principal) {
        log.info("principal class: {}", principal.getClass().getSimpleName());
        if (principal instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) principal;
            return userDetails.getUsername();
        }
        if (principal instanceof DefaultOAuth2AuthenticatedPrincipal) {
            DefaultOAuth2AuthenticatedPrincipal authenticatedPrincipal = (DefaultOAuth2AuthenticatedPrincipal) principal;
            log.info("DefaultOAuth2AuthenticatedPrincipal.attributes: {}", authenticatedPrincipal.getAttributes());
            log.info("DefaultOAuth2AuthenticatedPrincipal.authorities: {}", authenticatedPrincipal.getAuthorities());
            log.info("DefaultOAuth2AuthenticatedPrincipal.name: {}", authenticatedPrincipal.getName());
            return authenticatedPrincipal.getAttribute("user_name");
        }
        if (principal instanceof DefaultOAuth2User) {
            @SuppressWarnings("AlibabaLowerCamelCaseVariableNaming")
            DefaultOAuth2User defaultOAuth2User = (DefaultOAuth2User) principal;
            log.info("DefaultOAuth2User.attributes: {}", defaultOAuth2User.getAttributes());
            log.info("DefaultOAuth2User.authorities: {}", defaultOAuth2User.getAuthorities());
            log.info("DefaultOAuth2User.name: {}", defaultOAuth2User.getName());
            return defaultOAuth2User.getName();
        }
        return null;
    }
}
