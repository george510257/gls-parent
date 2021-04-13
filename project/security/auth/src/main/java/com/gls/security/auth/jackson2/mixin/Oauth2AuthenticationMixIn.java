package com.gls.security.auth.jackson2.mixin;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.gls.security.auth.jackson2.deserializer.Oauth2AuthenticationDeserializer;

/**
 * @author george
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, property = "@class")
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE)
@JsonDeserialize(using = Oauth2AuthenticationDeserializer.class)
public interface Oauth2AuthenticationMixIn {
}
