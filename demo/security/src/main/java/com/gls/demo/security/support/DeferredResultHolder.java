package com.gls.demo.security.support;

import org.springframework.web.context.request.async.DeferredResult;

import java.util.HashMap;
import java.util.Map;

/**
 * @author george
 */
public class DeferredResultHolder {
    private static final Map<String, DeferredResult<String>> DEFERRED_RESULT_HASH_MAP = new HashMap<String, DeferredResult<String>>();

    public static DeferredResult<String> getDeferredResult(String name) {
        return DEFERRED_RESULT_HASH_MAP.get(name);
    }

    public static void addDeferredResult(String name, DeferredResult<String> deferredResult) {
        DEFERRED_RESULT_HASH_MAP.put(name, deferredResult);
    }
}
