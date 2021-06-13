package com.gls.job.core.common.base;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author george
 */
@Slf4j
public abstract class BaseHolder<K, V> implements InitializingBean, DisposableBean {

    private final Map<K, V> map = new ConcurrentHashMap<>();

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

    public V load(K key) {
        return map.get(key);
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

    @Override
    public void destroy() throws Exception {
        removeAll("");
    }

}
