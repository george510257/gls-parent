package com.gls.job.admin.core.server;

import com.gls.job.admin.core.thread.JobScheduleRingThread;
import com.gls.job.admin.core.thread.JobScheduleThread;
import com.gls.job.admin.web.entity.JobInfo;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * @author george 2019-05-21
 */
@Slf4j
public class JobScheduleServer {
    public static final long PRE_READ_MS = 5000;
    private static final Map<Integer, List<Integer>> RING_DATA = new ConcurrentHashMap<>();
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
        scheduleThread = new JobScheduleThread(RING_DATA);
        scheduleThread.setDaemon(true);
        scheduleThread.setName("gls-job, admin JobScheduleHelper#scheduleThread");
        scheduleThread.start();

        // ring thread
        ringThread = new JobScheduleRingThread(RING_DATA);
        ringThread.setDaemon(true);
        ringThread.setName("gls-job, admin JobScheduleHelper#ringThread");
        ringThread.start();
    }

    public void toStop() {

        // 1、stop schedule
        scheduleThread.setToStop(true);
        try {
            // wait
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
        }
        if (scheduleThread.getState() != Thread.State.TERMINATED) {
            // interrupt and wait
            scheduleThread.interrupt();
            try {
                scheduleThread.join();
            } catch (InterruptedException e) {
                log.error(e.getMessage(), e);
            }
        }

        // if has ring data
        boolean hasRingData = false;
        if (!RING_DATA.isEmpty()) {
            for (int second : RING_DATA.keySet()) {
                List<Integer> tmpData = RING_DATA.get(second);
                if (tmpData != null && tmpData.size() > 0) {
                    hasRingData = true;
                    break;
                }
            }
        }
        if (hasRingData) {
            try {
                TimeUnit.SECONDS.sleep(8);
            } catch (InterruptedException e) {
                log.error(e.getMessage(), e);
            }
        }

        // stop ring (wait job-in-memory stop)
        ringThread.setToStop(true);
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
        }
        if (ringThread.getState() != Thread.State.TERMINATED) {
            // interrupt and wait
            ringThread.interrupt();
            try {
                ringThread.join();
            } catch (InterruptedException e) {
                log.error(e.getMessage(), e);
            }
        }

        log.info(">>>>>>>>>>> gls-job, JobScheduleHelper stop");
    }

}
