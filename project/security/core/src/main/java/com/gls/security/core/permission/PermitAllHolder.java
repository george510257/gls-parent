package com.gls.security.core.permission;

import org.springframework.http.HttpMethod;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author george
 */
public class PermitAllHolder {
    private final Map<String, HttpMethod> requestMatchers = new HashMap<>();

    public void addPermitAll(HttpMethod method, String... antPatterns) {
        Arrays.stream(antPatterns).forEach(antPattern -> {
            requestMatchers.put(antPattern, method);
        });
    }

    public void addPermitAll(String... antPatterns) {
        addPermitAll(null, antPatterns);
    }

    protected Map<String, HttpMethod> getPermitAllRequestMatchers() {
        return requestMatchers;
    }
}
