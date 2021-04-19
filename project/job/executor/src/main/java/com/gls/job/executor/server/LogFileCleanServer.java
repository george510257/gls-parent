package com.gls.job.executor.server;

import com.gls.job.executor.thread.LogFileCleanThread;
import lombok.extern.slf4j.Slf4j;

/**
 * @author george
 */
@Slf4j
public class LogFileCleanServer {

    private static final LogFileCleanServer INSTANCE = new LogFileCleanServer();
    private LogFileCleanThread localThread;

    public static LogFileCleanServer getInstance() {
        return INSTANCE;
    }

    public void start(final long logRetentionDays) {

        // limit min value
        if (logRetentionDays < 3) {
            return;
        }

        localThread = new LogFileCleanThread(logRetentionDays);
        localThread.setDaemon(true);
        localThread.setName("gls-job, executor JobLogFileCleanThread");
        localThread.start();
    }

    public void toStop() {
        localThread.setToStop(true);

        if (localThread == null) {
            return;
        }

        // interrupt and wait
        localThread.interrupt();
        try {
            localThread.join();
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
        }
    }

}
