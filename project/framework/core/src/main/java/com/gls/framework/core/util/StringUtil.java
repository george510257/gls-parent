package com.gls.framework.core.util;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author george
 */
public class StringUtil {

    public static List<String> toList(String s) {
        if (s == null || s.isEmpty()) {
            return Collections.<String>emptyList();
        }
        return Arrays.asList(s.split(","));
    }

    public static String toString(List<String> list) {
        if (list == null || list.isEmpty()) {
            return "";
        }
        return String.join(",", list);
    }

    public static Map<String, Object> toMap(String s) {
        if (s == null || s.isEmpty()) {
            return Collections.<String, Object>emptyMap();
        }
        List<String> list = Arrays.asList(s.split(","));
        Map<String, Object> map = new HashMap<>(list.size());
        list.forEach(s1 -> {
            String[] s2 = s1.split(":");
            map.put(s2[0], s2[1]);
        });
        return map;
    }

    public static String toString(Map<?, ?> map) {
        if (map == null || map.isEmpty()) {
            return "";
        }
        return map.entrySet().stream().map(entry -> entry.getKey() + ":" + entry.getValue()).collect(Collectors.joining(","));
    }
}
