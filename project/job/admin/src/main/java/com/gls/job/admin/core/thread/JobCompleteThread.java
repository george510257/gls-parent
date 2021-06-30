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
public class JobCompleteThread extends BaseThread {
    @Resource
    private JobLogService jobLogService;

    @Override
    protected void destroyExecute() {
        log.info(">>>>>>>>>>> gls-job, JobCompleteThread destroy");
    }

    @Override
    protected void doExecute() {
        jobLogService.doJobComplete();
    }

    @Override
    protected void initExecute() {
        try {
            TimeUnit.MILLISECONDS.sleep(50);
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
        }
    }

    @Override
    protected void sleepExecute() {
        try {
            TimeUnit.SECONDS.sleep(60);
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
        }
    }
}
