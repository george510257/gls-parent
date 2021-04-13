package com.gls.security.auth.jackson2.mixin;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.gls.security.auth.jackson2.deserializer.Oauth2AccessTokenDeserializer;
import com.gls.security.auth.jackson2.serializer.Oauth2AccessTokenSerializer;

/**
 * @author george
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, property = "@class")
@JsonSerialize(using = Oauth2AccessTokenSerializer.class)
@JsonDeserialize(using = Oauth2AccessTokenDeserializer.class)
public interface Oauth2AccessTokenMixIn {
}
