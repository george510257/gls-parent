package com.gls.security.core.web.service.impl;

import com.gls.framework.core.util.UrlUtil;
import com.gls.security.core.web.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

/**
 * @author george
 */
@Slf4j
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private static final String ADMIN_USERNAME = "admin";

    private static final String ADMIN_GRANTED_AUTHORITY = "admin";

    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("username: {}", username);
        String password = passwordEncoder.encode("123456");
        log.info("password: {}", password);
        return new User(username, password,
                true, true, true, true,
                AuthorityUtils.createAuthorityList("admin"));
    }

    @Override
    public boolean hasPermission(String requestUrl, String username) {
        if (username == null) {
            return false;
        }
        //如果用户名是admin，就永远返回true
        if (ADMIN_USERNAME.equalsIgnoreCase(username)) {
            return true;
        }
        UserDetails userDetails = loadUserByUsername(username);
        for (GrantedAuthority authority : userDetails.getAuthorities()) {
            log.info("authority: {}", authority.getAuthority());
            if (ADMIN_GRANTED_AUTHORITY.equalsIgnoreCase(authority.getAuthority())) {
                return true;
            }
        }
        // 读取用户所拥有权限的所有URL
        return UrlUtil.urlsContains(requestUrl, getUrls(userDetails));
    }

    private Set<String> getUrls(UserDetails userDetails) {
        return new HashSet<>();
    }
}
