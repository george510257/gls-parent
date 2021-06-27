package com.gls.security.app.config;

import com.gls.security.app.customizer.Oauth2ResourceServerCustomizer;
import com.gls.security.captcha.filter.CaptchaConfigurer;
import com.gls.security.core.customizer.AuthorizeRequestsCustomizer;
import com.gls.security.core.customizer.CsrfCustomizer;
import com.gls.security.core.customizer.FormLoginCustomizer;
import com.gls.security.core.customizer.HttpBasicCustomizer;
import com.gls.security.core.mobile.MobileConfigurer;
import com.gls.security.social.customizer.Oauth2LoginCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
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
    private Oauth2ResourceServerCustomizer oauth2ResourceServerCustomizer;
    @Resource
    private CaptchaConfigurer captchaConfigurer;
    @Resource
    private MobileConfigurer mobileConfigurer;
    @Resource
    private Oauth2LoginCustomizer oauth2LoginCustomizer;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests(authorizeRequestsCustomizer)
                .formLogin(formLoginCustomizer)
                .httpBasic(httpBasicCustomizer)
                .csrf(csrfCustomizer)
                .oauth2Login(oauth2LoginCustomizer)
                .oauth2ResourceServer(oauth2ResourceServerCustomizer)
                .apply(captchaConfigurer).and()
                .apply(mobileConfigurer).and();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(HttpMethod.OPTIONS, "/**");
    }

    @Bean(name = "authenticationManager")
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
