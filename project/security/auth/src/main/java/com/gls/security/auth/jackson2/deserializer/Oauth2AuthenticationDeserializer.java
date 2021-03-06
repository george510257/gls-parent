package com.gls.security.auth.jackson2.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;

import java.io.IOException;

/**
 * @author george
 */
public class Oauth2AuthenticationDeserializer extends JsonDeserializer<OAuth2Authentication> {
    @Override
    public OAuth2Authentication deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        ObjectMapper objectMapper = (ObjectMapper) p.getCodec();
        JsonNode jsonNode = objectMapper.readTree(p);
        // storedRequest
        JsonNode storedRequestJsonNode = jsonNode.get("storedRequest");
        OAuth2Request storedRequest = objectMapper.readValue(storedRequestJsonNode.traverse(objectMapper), new TypeReference<OAuth2Request>() {
        });
        // userAuthentication
        JsonNode userAuthenticationJsonNode = jsonNode.get("userAuthentication");
        Authentication userAuthentication = null;
        if (userAuthenticationJsonNode != null && !userAuthenticationJsonNode.isMissingNode()) {
            userAuthentication = objectMapper.readValue(userAuthenticationJsonNode.traverse(objectMapper), new TypeReference<Authentication>() {
            });
        }
        OAuth2Authentication auth2Authentication = new OAuth2Authentication(storedRequest, userAuthentication);
        // details
        JsonNode detailsJsonNode = jsonNode.get("details");
        if (detailsJsonNode != null && !detailsJsonNode.isMissingNode()) {
            auth2Authentication.setDetails(objectMapper.readValue(detailsJsonNode.traverse(objectMapper), new TypeReference<Object>() {
            }));
        }
        return auth2Authentication;
    }
}
