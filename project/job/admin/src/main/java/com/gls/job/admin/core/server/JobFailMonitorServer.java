package com.gls.job.admin.core.server;

import com.gls.job.admin.core.thread.JobFailMonitorThread;
import lombok.extern.slf4j.Slf4j;

/**
 * job monitor instance
 *
 * @author george 2015-9-1 18:05:56
 */
@Slf4j
public class JobFailMonitorServer {

    private static final JobFailMonitorServer INSTANCE = new JobFailMonitorServer();
    private JobFailMonitorThread monitorThread;

    // ---------------------- monitor ----------------------

    public static JobFailMonitorServer getInstance() {
        return INSTANCE;
    }

    public void start() {
        monitorThread = new JobFailMonitorThread();
        monitorThread.setDaemon(true);
        monitorThread.setName("gls-job, admin JobFailMonitorHelper");
        monitorThread.start();
    }

    public void toStop() {
        monitorThread.toStop("");
    }

}
