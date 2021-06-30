package com.gls.job.admin.core.thread;

import com.gls.job.admin.web.service.JobInfoService;
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
    private JobInfoService jobInfoService;

    @Override
    protected void destroyExecute() {
        log.info(">>>>>>>>>>> gls-job, JobScheduleHelper#ringThread stop");
    }

    @Override
    protected void doExecute() {
        jobInfoService.doJobScheduleRing();
    }

    @Override
    protected void initExecute() {
    }

    @Override
    protected void sleepExecute() {
        try {
            TimeUnit.MILLISECONDS.sleep(1000 - System.currentTimeMillis() % 1000);
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
        }
    }
}
