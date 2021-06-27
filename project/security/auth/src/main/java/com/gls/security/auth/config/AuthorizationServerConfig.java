package com.gls.security.auth.config;

import com.gls.security.auth.granter.ImagesCaptchaTokenGranter;
import com.gls.security.auth.granter.SmsCaptchaTokenGranter;
import com.gls.security.auth.web.service.ClientService;
import com.gls.security.captcha.web.service.impl.ImagesCaptchaService;
import com.gls.security.captcha.web.service.impl.SmsCaptchaService;
import com.gls.security.core.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.CompositeTokenGranter;
import org.springframework.security.oauth2.provider.TokenGranter;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import javax.annotation.Resource;
import java.util.Collections;

/**
 * @author george
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
    @Resource
    private AuthenticationManager authenticationManager;
    @Resource
    private ClientService clientService;
    @Resource
    private UserService userService;
    @Resource
    private TokenStore tokenStore;
    @Autowired(required = false)
    private JwtAccessTokenConverter jwtAccessTokenConverter;
    @Resource
    private ImagesCaptchaService imagesCaptchaService;
    @Resource
    private SmsCaptchaService smsCaptchaService;

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.allowFormAuthenticationForClients()
                .tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()");
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(clientService);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.userDetailsService(userService)
                //指定认证管理器
                .authenticationManager(authenticationManager)
                //指定token存储位置
                .tokenStore(tokenStore);
        if (jwtAccessTokenConverter != null) {
            //配置JwtAccessToken转换器
            endpoints.tokenEnhancer(jwtAccessTokenConverter);
        }
        endpoints.tokenGranter(getTokenGranter(endpoints));
    }

    private TokenGranter getTokenGranter(AuthorizationServerEndpointsConfigurer endpoints) {
        CompositeTokenGranter tokenGranter = new CompositeTokenGranter(Collections.singletonList(endpoints.getTokenGranter()));
        tokenGranter.addTokenGranter(new ImagesCaptchaTokenGranter(imagesCaptchaService, authenticationManager, endpoints.getTokenServices(), endpoints.getClientDetailsService(), endpoints.getOAuth2RequestFactory()));
        tokenGranter.addTokenGranter(new SmsCaptchaTokenGranter(smsCaptchaService, authenticationManager, endpoints.getTokenServices(), endpoints.getClientDetailsService(), endpoints.getOAuth2RequestFactory()));
        return tokenGranter;
    }
}
