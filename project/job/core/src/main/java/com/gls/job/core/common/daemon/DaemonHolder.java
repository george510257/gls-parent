package com.gls.job.core.common.daemon;

import com.gls.job.core.common.base.BaseHolder;
import com.gls.job.core.common.base.BaseThread;
import org.springframework.util.ObjectUtils;

import java.util.Map;

/**
 * @author george
 */
public class DaemonHolder extends BaseHolder<String, Daemon<? extends BaseThread>> {

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
}
