package com.gls.security.auth.jackson2;

import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.security.oauth2.provider.token.store.redis.StandardStringSerializationStrategy;

/**
 * @author george
 */
public class Jackson2SerializationStrategy extends StandardStringSerializationStrategy {
    private static GenericJackson2JsonRedisSerializer jsonRedisSerializer;

    public Jackson2SerializationStrategy(GenericJackson2JsonRedisSerializer genericJackson2JsonRedisSerializer) {
        jsonRedisSerializer = genericJackson2JsonRedisSerializer;
    }

    @Override
    protected <T> T deserializeInternal(byte[] bytes, Class<T> clazz) {
        return jsonRedisSerializer.deserialize(bytes, clazz);
    }

    @Override
    protected byte[] serializeInternal(Object object) {
        return jsonRedisSerializer.serialize(object);
    }
}
