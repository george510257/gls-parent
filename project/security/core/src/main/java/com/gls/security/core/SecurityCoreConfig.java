package com.gls.security.core;

import com.gls.security.core.constants.SecurityProperties;
import com.gls.security.core.web.service.PermissionService;
import com.gls.security.core.web.service.UserService;
import com.gls.security.core.web.service.impl.PermissionServiceImpl;
import com.gls.security.core.web.service.impl.UserServiceImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author george
 */
@Configuration
@ComponentScan
@EnableConfigurationProperties(SecurityProperties.class)
public class SecurityCoreConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    @ConditionalOnMissingBean(UserService.class)
    public UserService userService(PasswordEncoder passwordEncoder) {
        return new UserServiceImpl(passwordEncoder);
    }

    @Bean
    @ConditionalOnMissingBean(PermissionService.class)
    public PermissionService permissionService(UserService userService) {
        return new PermissionServiceImpl(userService);
    }
}
