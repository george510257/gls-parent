package com.gls.job.core.daemon;

import com.gls.job.core.base.BaseHolder;
import com.gls.job.core.base.BaseThread;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author george
 */
@Component
public class DaemonHolder extends BaseHolder<String, Daemon<? extends BaseThread>> {
    @Resource
    private Map<String, BaseThread> daemonThreads;

    public void registByThread(Map<String, ? extends BaseThread> threads) {
        threads.forEach((key, value) -> {
            registByThread(key, value, "");
        });
    }

    public void registByThread(String key, BaseThread value, String reason) {
        Daemon<? extends BaseThread> daemon = new Daemon<>(key, value);
        daemon.start();
        regist(key, daemon, reason);
    }

    @Override
    protected void delete(Daemon<? extends BaseThread> oldValue, String reason) {
        if (ObjectUtils.isEmpty(oldValue)) {
            return;
        }
        oldValue.stop(reason);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        registByThread(daemonThreads);
    }
}
