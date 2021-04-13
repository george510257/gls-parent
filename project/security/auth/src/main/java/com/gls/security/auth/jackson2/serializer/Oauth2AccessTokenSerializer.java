package com.gls.security.auth.jackson2.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessTokenJackson2Serializer;

import java.io.IOException;

/**
 * @author george
 */
public class Oauth2AccessTokenSerializer extends StdSerializer<OAuth2AccessToken> {

    private static final OAuth2AccessTokenJackson2Serializer SERIALIZER = new OAuth2AccessTokenJackson2Serializer();

    public Oauth2AccessTokenSerializer() {
        super(OAuth2AccessToken.class);
    }

    @Override
    public void serialize(OAuth2AccessToken value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        SERIALIZER.serialize(value, gen, provider);
    }

    @Override
    public void serializeWithType(OAuth2AccessToken value, JsonGenerator gen, SerializerProvider serializers, TypeSerializer typeSer) throws IOException {
        SERIALIZER.serialize(value, gen, serializers);
    }
}
