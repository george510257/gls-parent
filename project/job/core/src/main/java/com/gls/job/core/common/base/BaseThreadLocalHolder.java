package com.gls.job.core.common.base;

/**
 * @author george
 */
public abstract class BaseThreadLocalHolder<T> {

    private final InheritableThreadLocal<T> inheritableThreadLocal = new InheritableThreadLocal<>();

    public T get() {
        return inheritableThreadLocal.get();
    }

    public void set(T t) {
        inheritableThreadLocal.set(t);
    }
}
