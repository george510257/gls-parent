package com.gls.framework.core.util;

import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;

/**
 * @author george
 */
public class StringUtil {
    public static List<String> toList(String s) {
        if (StringUtils.hasText(s)) {
            return Arrays.asList(s.split(","));
        }
        return null;
    }

    public static String toString(List<String> list) {
        if (ObjectUtils.isEmpty(list)) {
            return null;
        }
        return String.join(",", list);
    }
}
