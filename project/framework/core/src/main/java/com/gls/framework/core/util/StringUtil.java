package com.gls.framework.core.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author george
 */
public class StringUtil {

    public static List<String> toList(String s) {
        if (s == null || s.isEmpty()) {
            return Collections.emptyList();
        }
        return Arrays.asList(s.split(","));
    }

    public static String toString(List<String> list) {
        if (list == null || list.isEmpty()) {
            return "";
        }
        return String.join(",", list);
    }
}
