package com.gls.job.core.base.daemon;

import com.gls.job.core.base.thread.BaseThread;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author george
 */
@Slf4j
public class DaemonFactory {

    private final ConcurrentMap<String, Daemon<? extends BaseThread>> daemons = new ConcurrentHashMap<>();

    public Daemon<? extends BaseThread> load(String name) {
        return daemons.get(name);
    }

    public void regist(Map<String, ? extends BaseThread> runnerMap) {
        runnerMap.forEach((key, value) -> {
            daemons.put(key, new Daemon<>(key, value));
        });
    }
}
