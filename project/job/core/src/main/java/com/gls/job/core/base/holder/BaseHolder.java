package com.gls.job.core.base.holder;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

/**
 * @author george
 */
public abstract class BaseHolder<K, V> {

    private final Map<K, V> map = new ConcurrentHashMap<>();

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

    public V computeIfAbsent(K key, Function<? super K, ? extends V> mappingFunction) {
        return map.computeIfAbsent(key, mappingFunction);
    }
}
