package com.gls.job.core.base.holder;

/**
 * @author george
 */
public class BaseThreadLocalHolder<T> {

    private final InheritableThreadLocal<T> inheritableThreadLocal = new InheritableThreadLocal<>();

    public T get() {
        return inheritableThreadLocal.get();
    }

    public void set(T t) {
        inheritableThreadLocal.set(t);
    }
}
