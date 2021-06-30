package com.gls.job.core.base;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author george
 */
public abstract class BaseQueueHolder<V> {
    private final LinkedBlockingQueue<V> queue = new LinkedBlockingQueue<>();

    public boolean isEmpty() {
        return queue.isEmpty();
    }

    public V pop() {
        return queue.remove();
    }

    public List<V> pops() {
        List<V> list = Collections.synchronizedList(new ArrayList<>());
        queue.drainTo(list);
        return list;
    }

    public boolean push(List<V> list) {
        return queue.addAll(list);
    }

    public boolean push(V value) {
        return queue.add(value);
    }
}
