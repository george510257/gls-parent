package com.gls.job.admin.core.thread;

import com.gls.job.admin.web.service.JobLogService;
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
public class JobFailThread extends BaseThread {
    @Resource
    private JobLogService jobLogService;

    @Override
    protected void initExecute() {
        log.info(">>>>>>>>>>> gls-job, JobFailThread init");
    }

    @Override
    protected void doExecute() {
        jobLogService.doJobFail();
    }

    @Override
    protected void sleepExecute() {
        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
        }
    }

    @Override
    protected void destroyExecute() {
        log.info(">>>>>>>>>>> gls-job, JobFailThread destroy");
    }
}
