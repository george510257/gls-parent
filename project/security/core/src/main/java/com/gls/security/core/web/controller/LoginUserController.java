package com.gls.security.core.web.controller;

import com.gls.security.core.web.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author george
 */
@Slf4j
@RestController
@RequestMapping("/loginUser")
public class LoginUserController {

    @Resource
    private UserService userService;

    @GetMapping("/me")
    public UserDetails getLoginUser(Authentication authentication) {
        Object principal = authentication.getPrincipal();
        return getUserDetails(principal);
    }

    private UserDetails getUserDetails(Object principal) {
        if (principal instanceof UserDetails) {
            return (UserDetails) principal;
        }
        if (principal instanceof OAuth2AuthenticatedPrincipal) {
            OAuth2AuthenticatedPrincipal oAuth2AuthenticatedPrincipal = (OAuth2AuthenticatedPrincipal) principal;
            String username = oAuth2AuthenticatedPrincipal.getAttribute("user_name");
            return userService.loadUserByUsername(username);
        }
        return null;
    }
}
