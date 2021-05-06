package com.gls.job.admin.core.server;

import com.gls.job.admin.core.thread.JobScheduleRingThread;
import com.gls.job.admin.core.thread.JobScheduleThread;
import com.gls.job.admin.web.entity.JobInfo;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

/**
 * @author george 2019-05-21
 */
@Slf4j
public class JobScheduleServer {
    public static final long PRE_READ_MS = 5000;
    private static final JobScheduleServer INSTANCE = new JobScheduleServer();
    private JobScheduleThread scheduleThread;
    private JobScheduleRingThread ringThread;

    public static JobScheduleServer getInstance() {
        return INSTANCE;
    }

    public static Date generateNextValidTime(JobInfo jobInfo, Date date) throws Exception {
        return JobScheduleThread.generateNextValidTime(jobInfo, date);
    }

    public void start() {

        // schedule thread
        scheduleThread = new JobScheduleThread();
        scheduleThread.setDaemon(true);
        scheduleThread.setName("gls-job, admin JobScheduleHelper#scheduleThread");
        scheduleThread.start();

        // ring thread
        ringThread = new JobScheduleRingThread();
        ringThread.setDaemon(true);
        ringThread.setName("gls-job, admin JobScheduleHelper#ringThread");
        ringThread.start();
    }

    public void toStop() {

        // 1、stop schedule
        scheduleThread.toStop("");
        // stop ring (wait job-in-memory stop)
        ringThread.toStop("");
        log.info(">>>>>>>>>>> gls-job, JobScheduleHelper stop");
    }

}
