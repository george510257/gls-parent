package com.gls.common.auth.web.service.impl;

import com.gls.common.auth.web.model.UserDetailsModel;
import com.gls.common.auth.web.service.UserDetailsService;
import com.gls.common.user.api.rpc.UserApi;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author george
 */
@Slf4j
@Service(value = "userService")
public class UserDetailsServiceImpl implements UserDetailsService {
    @DubboReference
    private UserApi userApi;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new UserDetailsModel(userApi.loadUserByUsername(username));
    }

    @Override
    public boolean hasPermission(String requestUrl, String username) {
        log.info("requestUrl: {}", requestUrl);
        log.info("username: {}", username);
        return true;
    }
}
