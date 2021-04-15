package com.gls.security.social.jackson2.mixin;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.gls.security.social.jackson2.deserializer.OAuth2AuthorizationResponseTypeDeserializer;

/**
 * @author george
 */
@SuppressWarnings("AlibabaClassNamingShouldBeCamel")
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, property = "@class")
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE)
@JsonDeserialize(using = OAuth2AuthorizationResponseTypeDeserializer.class)
public interface OAuth2AuthorizationResponseTypeMixin {
}
