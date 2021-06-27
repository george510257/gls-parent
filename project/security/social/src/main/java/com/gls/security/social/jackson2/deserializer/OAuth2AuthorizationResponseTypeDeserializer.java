package com.gls.security.social.jackson2.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationResponseType;

import java.io.IOException;

/**
 * @author george
 */
@SuppressWarnings("AlibabaClassNamingShouldBeCamel")
public class OAuth2AuthorizationResponseTypeDeserializer extends StdDeserializer<OAuth2AuthorizationResponseType> {
    private static final String CODE = "code";
    private static final String TOKEN = "token";

    protected OAuth2AuthorizationResponseTypeDeserializer() {
        super(OAuth2AuthorizationResponseType.class);
    }

    @Override
    public OAuth2AuthorizationResponseType deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        ObjectMapper objectMapper = (ObjectMapper) p.getCodec();
        JsonNode jsonNode = objectMapper.readTree(p);
        JsonNode valueJsonNode = jsonNode.get("value");
        String value = objectMapper.readValue(valueJsonNode.traverse(objectMapper), String.class);
        if (CODE.equals(value)) {
            return OAuth2AuthorizationResponseType.CODE;
        } else if (TOKEN.equals(value)) {
            return OAuth2AuthorizationResponseType.TOKEN;
        }
        return null;
    }
}
