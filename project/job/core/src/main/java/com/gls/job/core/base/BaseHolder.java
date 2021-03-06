package com.gls.job.core.base;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author george
 */
@Slf4j
public abstract class BaseHolder<K, V> implements InitializingBean, DisposableBean {
    private final Map<K, V> map = new ConcurrentHashMap<>();

    @Override
    public void destroy() throws Exception {
        removeAll("");
    }

    public V load(K key) {
        return map.get(key);
    }

    public List<V> loadAll() {
        return new ArrayList<>(map.values());
    }

    public void regist(Map<K, V> map) {
        map.forEach((key, value) -> {
            regist(key, value, "");
        });
    }

    public void regist(K key, V value, String reason) {
        log.info(">>>>>>>>>>> regist {} [key:{},value:{}]", this.getClass().getSimpleName(), key, value.getClass().getSimpleName());
        V oldValue = map.put(key, value);
        delete(oldValue, reason);
    }

    public V remove(K key, String reason) {
        V oldValue = map.remove(key);
        delete(oldValue, reason);
        return oldValue;
    }

    public void removeAll(String reason) {
        for (K key : map.keySet()) {
            remove(key, reason);
        }
    }

    /**
     * delete
     *
     * @param oldValue
     * @param reason
     */
    protected abstract void delete(V oldValue, String reason);
}
