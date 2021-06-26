package com.gls.framework.core.util;

import org.springframework.util.AntPathMatcher;

import java.util.Set;

/**
 * @author george
 */
public class UrlUtil {

    private static final AntPathMatcher ANT_PATH_MATCHER = new AntPathMatcher();

    public static boolean urlsContains(String requestUrl, Set<String> urls) {
        boolean flag = false;

        for (String url : urls) {
            if (ANT_PATH_MATCHER.match(url, requestUrl)) {
                flag = true;
                break;
            }
        }
        return flag;
    }
}
