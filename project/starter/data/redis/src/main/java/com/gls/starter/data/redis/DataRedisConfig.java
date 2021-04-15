package com.gls.starter.data.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import javax.annotation.Resource;

/**
 * @author george
 */
@Configuration
@ComponentScan
public class DataRedisConfig {

    @Resource
    private RedisConnectionFactory redisConnectionFactory;

    @Autowired(required = false)
    private StringRedisSerializer stringRedisSerializer;

    @Autowired(required = false)
    private GenericJackson2JsonRedisSerializer genericJackson2JsonRedisSerializer;

    @Bean
    public RedisTemplate<Object, Object> redisTemplate() {
        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);

        if (stringRedisSerializer == null) {
            stringRedisSerializer = new StringRedisSerializer();
        }
        redisTemplate.setKeySerializer(stringRedisSerializer);
        redisTemplate.setHashKeySerializer(stringRedisSerializer);

        if (genericJackson2JsonRedisSerializer == null) {
            genericJackson2JsonRedisSerializer = new GenericJackson2JsonRedisSerializer();
        }
        redisTemplate.setValueSerializer(genericJackson2JsonRedisSerializer);
        redisTemplate.setHashValueSerializer(genericJackson2JsonRedisSerializer);

        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }
}
