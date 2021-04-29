package com.gls.job.core.base.daemon;

import com.gls.job.core.base.thread.BaseThread;

/**
 * @author george
 */
public class Daemon<DaemonThread extends BaseThread> {

    private final String name;
    private final DaemonThread daemonThread;

    public Daemon(String name, DaemonThread daemonThread) {
        this.name = name;
        this.daemonThread = daemonThread;
        start();
    }

    public void start() {
        daemonThread.setName(name);
        daemonThread.setDaemon(true);
        daemonThread.start();
    }

    public void stop(String stopReason) {
        daemonThread.toStop(stopReason);
    }
}
