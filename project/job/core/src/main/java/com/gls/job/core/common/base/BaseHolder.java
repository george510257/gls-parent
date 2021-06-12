package com.gls.job.core.common.base;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author george
 */
public abstract class BaseHolder<K, V> {

    private final Map<K, V> map = new ConcurrentHashMap<>();

    public void regist(Map<K, V> map) {
        map.forEach((key, value) -> {
            regist(key, value, "");
        });
    }

    public void regist(K key, V value, String reason) {
        V oldValue = map.put(key, value);
        delete(oldValue, reason);
    }

    public V load(K key) {
        return map.get(key);
    }

    public V remove(K key, String reason) {
        V oldValue = map.remove(key);
        delete(oldValue, reason);
        return oldValue;
    }

    protected void delete(V oldValue, String reason) {
    }
}
