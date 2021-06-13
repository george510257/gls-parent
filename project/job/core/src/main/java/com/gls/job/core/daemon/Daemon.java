package com.gls.job.core.daemon;

import com.gls.job.core.base.BaseThread;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author george
 */
@Getter
@AllArgsConstructor
public class Daemon<Thread extends BaseThread> {
    private final String name;
    private final Thread thread;

    public void start() {
        thread.setName(name);
        thread.setDaemon(true);
        thread.start();
    }

    public void stop(String stopReason) {
        thread.toStop(stopReason);
    }
}
