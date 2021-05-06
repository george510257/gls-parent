package com.gls.job.admin.core.server;

import com.gls.job.admin.core.thread.JobLogReportThread;
import lombok.extern.slf4j.Slf4j;

/**
 * job log report helper
 *
 * @author george 2019-11-22
 */
@Slf4j
public class JobLogReportServer {

    private static final JobLogReportServer INSTANCE = new JobLogReportServer();
    private JobLogReportThread jobLogReportThread;

    public static JobLogReportServer getInstance() {
        return INSTANCE;
    }

    public void start() {
        jobLogReportThread = new JobLogReportThread();
        jobLogReportThread.setDaemon(true);
        jobLogReportThread.setName("gls-job, admin JobLogReportHelper");
        jobLogReportThread.start();
    }

    public void toStop() {
        jobLogReportThread.toStop("");
    }

}
