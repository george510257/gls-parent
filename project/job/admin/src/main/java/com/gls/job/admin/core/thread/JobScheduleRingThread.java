package com.gls.job.admin.core.thread;

import com.gls.job.admin.core.server.JobTriggerPoolHelper;
import com.gls.job.admin.web.entity.enums.TriggerType;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author george
 */
@Slf4j
public class JobScheduleRingThread extends Thread {

    private final Map<Integer, List<Integer>> ringData;
    @Setter
    private volatile boolean toStop = false;

    public JobScheduleRingThread(Map<Integer, List<Integer>> ringData) {
        this.ringData = ringData;
    }

    @Override
    public void run() {
        while (!toStop) {

            // align second
            try {
                TimeUnit.MILLISECONDS.sleep(1000 - System.currentTimeMillis() % 1000);
            } catch (InterruptedException e) {
                if (!toStop) {
                    log.error(e.getMessage(), e);
                }
            }

            try {
                // second data
                List<Integer> ringItemData = new ArrayList<>();
                int nowSecond = Calendar.getInstance().get(Calendar.SECOND);
                // 避免处理耗时太长，跨过刻度，向前校验一个刻度；
                for (int i = 0; i < 2; i++) {
                    List<Integer> tmpData = ringData.remove((nowSecond + 60 - i) % 60);
                    if (tmpData != null) {
                        ringItemData.addAll(tmpData);
                    }
                }

                // ring trigger
                log.debug(">>>>>>>>>>> gls-job, time-ring beat : " + nowSecond + " = " + Collections.singletonList(ringItemData));
                if (ringItemData.size() > 0) {
                    // do trigger
                    for (int jobId : ringItemData) {
                        // do trigger
                        JobTriggerPoolHelper.trigger(jobId, TriggerType.CRON, -1, null, null, null);
                    }
                    // clear
                    ringItemData.clear();
                }
            } catch (Exception e) {
                if (!toStop) {
                    log.error(">>>>>>>>>>> gls-job, JobScheduleHelper#ringThread error:{}", e);
                }
            }
        }
        log.info(">>>>>>>>>>> gls-job, JobScheduleHelper#ringThread stop");
    }
}
