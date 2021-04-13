package com.gls.security.core.customizer;

import com.gls.security.core.permission.PermitAllManager;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author george
 */
@Component
public class AuthorizeRequestsCustomizer implements Customizer<ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry> {

    @Resource
    private PermitAllManager permitAllManager;

    @Override
    public void customize(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry) {

        Map<String, HttpMethod> permitAllRequestMatchers = permitAllManager.getPermitAllRequestMatchers();
        permitAllRequestMatchers.forEach((key, value) -> {
            if (value != null) {
                registry.antMatchers(value, key).permitAll();
            } else {
                registry.antMatchers(key).permitAll();
            }
        });
        registry.anyRequest().access("@permissionService.hasPermission(authentication)");
    }

}
