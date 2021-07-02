package com.gls.job.admin.core.thread;

import com.gls.job.admin.web.service.JobScheduleService;
import com.gls.job.core.base.BaseThread;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author george
 */
@Slf4j
@Component
public class JobScheduleRingThread extends BaseThread {
    @Resource
    private JobScheduleService jobScheduleService;

    @Override
    protected void destroyExecute() {
        log.info(">>>>>>>>>>> gls-job, JobScheduleHelper#ringThread stop");
    }

    @Override
    protected void doExecute() {
        jobScheduleService.ring();
    }

    @Override
    protected void initExecute() {
    }

    @Override
    protected void sleepExecute() throws Exception {
        TimeUnit.MILLISECONDS.sleep(1000 - System.currentTimeMillis() % 1000);
    }
}
