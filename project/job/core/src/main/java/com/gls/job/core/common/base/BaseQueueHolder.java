package com.gls.job.core.common.base;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author george
 */
public abstract class BaseQueueHolder<V> {

    private final LinkedBlockingQueue<V> queue = new LinkedBlockingQueue<>();

    public boolean push(V value) {
        return queue.add(value);
    }

    public boolean push(List<V> list) {
        return queue.addAll(list);
    }

    public V pop() {
        return queue.remove();
    }

    public List<V> pops() {
        List<V> list = Collections.synchronizedList(new ArrayList<>());
        queue.drainTo(list);
        return list;
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }
}
