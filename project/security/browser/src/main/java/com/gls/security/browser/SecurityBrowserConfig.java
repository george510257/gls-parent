package com.gls.security.browser;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gls.security.browser.handler.DefaultFailureHandler;
import com.gls.security.browser.handler.DefaultLogoutSuccessHandler;
import com.gls.security.browser.handler.DefaultSuccessHandler;
import com.gls.security.browser.session.DefaultExpiredSessionStrategy;
import com.gls.security.browser.session.DefaultInvalidSessionStrategy;
import com.gls.security.core.constants.SecurityProperties;
import com.gls.security.core.mobile.token.MobileModule;
import com.gls.security.social.jackson2.OAuth2LoginModule;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.security.jackson2.CoreJackson2Module;
import org.springframework.security.oauth2.client.jackson2.OAuth2ClientJackson2Module;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.rememberme.InMemoryTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.jackson2.WebJackson2Module;
import org.springframework.security.web.jackson2.WebServletJackson2Module;
import org.springframework.security.web.server.jackson2.WebServerJackson2Module;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

import javax.annotation.Resource;

/**
 * @author george
 */
@Configuration
@ComponentScan
@EnableRedisHttpSession
public class SecurityBrowserConfig {

    @Resource
    private SecurityProperties securityProperties;

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
    @ConditionalOnMissingBean(LogoutSuccessHandler.class)
    public LogoutSuccessHandler logoutSuccessHandler() {
        return new DefaultLogoutSuccessHandler();
    }

    @Bean
    @ConditionalOnMissingBean(SessionInformationExpiredStrategy.class)
    public SessionInformationExpiredStrategy expiredSessionStrategy() {
        return new DefaultExpiredSessionStrategy(securityProperties);
    }

    @Bean
    @ConditionalOnMissingBean(InvalidSessionStrategy.class)
    public InvalidSessionStrategy invalidSessionStrategy() {
        return new DefaultInvalidSessionStrategy(securityProperties);
    }

    @Bean
    @ConditionalOnMissingBean(PersistentTokenRepository.class)
    public PersistentTokenRepository tokenRepository() {
        return new InMemoryTokenRepositoryImpl();
    }

    @Bean
    public RedisSerializer<Object> springSessionDefaultRedisSerializer(GenericJackson2JsonRedisSerializer genericJackson2JsonRedisSerializer) {
        return genericJackson2JsonRedisSerializer;
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
        return new GenericJackson2JsonRedisSerializer(mapper);
    }
}
