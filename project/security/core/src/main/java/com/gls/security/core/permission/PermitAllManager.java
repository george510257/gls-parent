package com.gls.security.core.permission;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author george
 */
@Component
public class PermitAllManager {

    private final PermitAllHolder holder = new PermitAllHolder();

    @Resource
    private List<PermitAllProvider> permitAllProviders;

    public Map<String, HttpMethod> getPermitAllRequestMatchers() {
        permitAllProviders.forEach(provider -> {
            provider.config(holder);
        });
        return holder.getPermitAllRequestMatchers();
    }
}
