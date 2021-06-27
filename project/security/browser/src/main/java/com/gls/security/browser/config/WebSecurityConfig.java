package com.gls.security.browser.config;

import com.gls.security.browser.customizer.LogoutCustomizer;
import com.gls.security.browser.customizer.RememberMeCustomizer;
import com.gls.security.browser.customizer.SessionManagementCustomizer;
import com.gls.security.captcha.filter.CaptchaConfigurer;
import com.gls.security.core.customizer.AuthorizeRequestsCustomizer;
import com.gls.security.core.customizer.CsrfCustomizer;
import com.gls.security.core.customizer.FormLoginCustomizer;
import com.gls.security.core.customizer.HttpBasicCustomizer;
import com.gls.security.core.mobile.MobileConfigurer;
import com.gls.security.social.customizer.Oauth2LoginCustomizer;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import javax.annotation.Resource;

/**
 * @author george
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Resource
    private FormLoginCustomizer formLoginCustomizer;
    @Resource
    private HttpBasicCustomizer httpBasicCustomizer;
    @Resource
    private AuthorizeRequestsCustomizer authorizeRequestsCustomizer;
    @Resource
    private CsrfCustomizer csrfCustomizer;
    @Resource
    private SessionManagementCustomizer sessionManagementCustomizer;
    @Resource
    private LogoutCustomizer logoutCustomizer;
    @Resource
    private RememberMeCustomizer rememberMeCustomizer;
    @Resource
    private CaptchaConfigurer captchaConfigurer;
    @Resource
    private MobileConfigurer mobileConfigurer;
    @Resource
    private Oauth2LoginCustomizer oauth2LoginCustomizer;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        super.configure(auth);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests(authorizeRequestsCustomizer)
                .formLogin(formLoginCustomizer)
                .httpBasic(httpBasicCustomizer)
                .csrf(csrfCustomizer)
                .oauth2Login(oauth2LoginCustomizer)
                .sessionManagement(sessionManagementCustomizer)
                .logout(logoutCustomizer)
                .rememberMe(rememberMeCustomizer)
                .apply(captchaConfigurer).and()
                .apply(mobileConfigurer).and();
    }
}
