package com.gls.job.admin.core.thread;

import com.gls.job.admin.core.enums.TriggerType;
import com.gls.job.admin.core.ring.RingDataHolder;
import com.gls.job.admin.web.service.JobTriggerService;
import com.gls.job.core.base.BaseThread;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author george
 */
@Slf4j
@Component
public class JobScheduleRingThread extends BaseThread {

    @Resource
    private RingDataHolder ringDataHolder;
    @Resource
    private JobTriggerService jobTriggerService;

    @Override
    protected void initExecute() throws Exception {

    }

    @Override
    protected void doExecute() throws Exception {
        // second data
        List<Long> ringItemData = new ArrayList<>();
        int nowSecond = Calendar.getInstance().get(Calendar.SECOND);
        // 避免处理耗时太长，跨过刻度，向前校验一个刻度；
        for (int i = 0; i < 2; i++) {
            List<Long> tmpData = ringDataHolder.remove((nowSecond + 60 - i) % 60, "");
            if (tmpData != null) {
                ringItemData.addAll(tmpData);
            }
        }

        // ring trigger
        log.debug(">>>>>>>>>>> gls-job, time-ring beat : " + nowSecond + " = " + Collections.singletonList(ringItemData));
        if (ringItemData.size() > 0) {
            // do trigger
            for (Long jobId : ringItemData) {
                // do trigger
                jobTriggerService.trigger(jobId, TriggerType.CRON, -1, null, null, null);
            }
            // clear
            ringItemData.clear();
        }
    }

    @Override
    protected void sleepExecute() throws Exception {
        TimeUnit.MILLISECONDS.sleep(1000 - System.currentTimeMillis() % 1000);
    }

    @Override
    protected void destroyExecute() throws Exception {
        log.info(">>>>>>>>>>> gls-job, JobScheduleHelper#ringThread stop");
    }

}
