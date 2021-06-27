package com.gls.security.auth.jackson2.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.springframework.security.oauth2.provider.OAuth2Request;

import java.io.IOException;
import java.io.Serializable;
import java.util.Map;
import java.util.Set;

/**
 * @author george
 */
public class Oauth2RequestDeserializer extends StdDeserializer<OAuth2Request> {
    protected Oauth2RequestDeserializer() {
        super(OAuth2Request.class);
    }

    @Override
    public OAuth2Request deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        ObjectMapper objectMapper = (ObjectMapper) p.getCodec();
        JsonNode jsonNode = objectMapper.readTree(p);
        // requestParameters
        JsonNode requestParametersJsonNode = jsonNode.get("requestParameters");
        Map requestParameters = objectMapper.readValue(requestParametersJsonNode.traverse(objectMapper), Map.class);
        // clientId
        JsonNode clientIdJsonNode = jsonNode.get("clientId");
        String clientId = objectMapper.readValue(clientIdJsonNode.traverse(objectMapper), String.class);
        // authorities
        JsonNode authoritiesJsonNode = jsonNode.get("authorities");
        Set authorities = objectMapper.readValue(authoritiesJsonNode.traverse(objectMapper), Set.class);
        // approved
        JsonNode approvedJsonNode = jsonNode.get("approved");
        boolean approved = objectMapper.readValue(approvedJsonNode.traverse(objectMapper), Boolean.class);
        // scope
        JsonNode scopeJsonNode = jsonNode.get("scope");
        Set scope = objectMapper.readValue(scopeJsonNode.traverse(objectMapper), Set.class);
        // resourceIds
        JsonNode resourceIdsJsonNode = jsonNode.get("resourceIds");
        Set resourceIds = objectMapper.readValue(resourceIdsJsonNode.traverse(objectMapper), Set.class);
        // redirectUri
        JsonNode redirectUriJsonNode = jsonNode.get("redirectUri");
        String redirectUri = objectMapper.readValue(redirectUriJsonNode.traverse(objectMapper), String.class);
        // responseTypes
        JsonNode responseTypesJsonNode = jsonNode.get("responseTypes");
        Set responseTypes = objectMapper.readValue(responseTypesJsonNode.traverse(objectMapper), Set.class);
        // extensionProperties
        JsonNode extensionPropertiesJsonNode = jsonNode.get("extensions");
        Map<String, Serializable> extensionProperties = objectMapper.readValue(extensionPropertiesJsonNode.traverse(objectMapper), Map.class);
        OAuth2Request auth2Request = new OAuth2Request(requestParameters, clientId, authorities, approved, scope, resourceIds, redirectUri, responseTypes, extensionProperties);
        return auth2Request;
    }
}
