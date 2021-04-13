package com.gls.security.auth.config;

import com.gls.security.auth.jackson2.Jackson2SerializationStrategy;
import com.gls.security.core.constants.SecurityConstants;
import com.gls.security.core.constants.SecurityProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import org.springframework.security.rsa.crypto.KeyStoreKeyFactory;

import javax.annotation.Resource;
import java.security.KeyPair;

/**
 * @author george
 */
@Configuration
public class TokenStoreConfig {

    @Configuration
    @ConditionalOnProperty(prefix = SecurityConstants.SECURITY_PROPERTIES_PREFIX + ".tokenStore", name = "type", havingValue = "redis", matchIfMissing = true)
    public static class RedisTokenStoreConfig {

        @Resource
        private RedisConnectionFactory redisConnectionFactory;

        @Resource
        private GenericJackson2JsonRedisSerializer genericJackson2JsonRedisSerializer;

        @Bean
        public TokenStore redisTokenStore() {
            RedisTokenStore tokenStore = new RedisTokenStore(redisConnectionFactory);
            tokenStore.setPrefix("token/");
            tokenStore.setSerializationStrategy(new Jackson2SerializationStrategy(genericJackson2JsonRedisSerializer));
            return tokenStore;
        }
    }

    @Configuration
    @ConditionalOnProperty(prefix = SecurityConstants.SECURITY_PROPERTIES_PREFIX + ".tokenStore", name = "type", havingValue = "jwt")
    public static class JwtTokenStoreConfig {

        @Resource
        private SecurityProperties securityProperties;

        @Bean
        public TokenStore tokenStore(JwtAccessTokenConverter jwtAccessTokenConverter) {
            return new JwtTokenStore(jwtAccessTokenConverter);
        }

        @Bean
        public JwtAccessTokenConverter jwtAccessTokenConverter() {
            JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
            jwtAccessTokenConverter.setKeyPair(keyPair());
            return jwtAccessTokenConverter;
        }

        @Bean
        public KeyPair keyPair() {
            SecurityProperties.Jwt jwt = securityProperties.getTokenStore().getJwt();
            KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(new ClassPathResource(jwt.getKeyStoreFile()), jwt.getKeyStorePassword().toCharArray());
            return keyStoreKeyFactory.getKeyPair(jwt.getKeyAlias(), jwt.getKeyPassword().toCharArray());
        }
    }
}
