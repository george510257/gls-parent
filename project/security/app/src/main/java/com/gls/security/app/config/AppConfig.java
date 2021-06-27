package com.gls.security.app.config;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gls.security.app.handler.DefaultFailureHandler;
import com.gls.security.app.handler.DefaultSuccessHandler;
import com.gls.security.auth.jackson2.Oauth2Module;
import com.gls.security.auth.web.service.ClientService;
import com.gls.security.auth.web.service.impl.ClientServiceImpl;
import com.gls.security.core.mobile.token.MobileModule;
import com.gls.security.social.jackson2.OAuth2LoginModule;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.jackson2.CoreJackson2Module;
import org.springframework.security.oauth2.client.jackson2.OAuth2ClientJackson2Module;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.jackson2.WebJackson2Module;
import org.springframework.security.web.jackson2.WebServletJackson2Module;
import org.springframework.security.web.server.jackson2.WebServerJackson2Module;

import javax.annotation.Resource;

/**
 * @author george
 */
@Configuration
public class AppConfig {
    @Resource
    private PasswordEncoder passwordEncoder;

    @Bean
    @ConditionalOnMissingBean(AuthenticationSuccessHandler.class)
    public AuthenticationSuccessHandler successHandler() {
        return new DefaultSuccessHandler();
    }

    @Bean
    @ConditionalOnMissingBean(AuthenticationFailureHandler.class)
    public AuthenticationFailureHandler failureHandler() {
        return new DefaultFailureHandler();
    }

    @Bean
    @ConditionalOnMissingBean(ClientService.class)
    public ClientService clientService() {
        return new ClientServiceImpl(passwordEncoder);
    }

    @Bean
    public GenericJackson2JsonRedisSerializer genericJackson2JsonRedisSerializer() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.activateDefaultTyping(mapper.getPolymorphicTypeValidator(), ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);
        mapper.disable(MapperFeature.AUTO_DETECT_SETTERS);
        mapper.registerModule(new CoreJackson2Module());
        mapper.registerModule(new WebJackson2Module());
        mapper.registerModule(new WebServletJackson2Module());
        mapper.registerModule(new WebServerJackson2Module());
        mapper.registerModule(new MobileModule());
        mapper.registerModule(new OAuth2ClientJackson2Module());
        mapper.registerModule(new OAuth2LoginModule());
        mapper.registerModule(new Oauth2Module());
        return new GenericJackson2JsonRedisSerializer(mapper);
    }
}
