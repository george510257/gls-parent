package com.gls.job.admin.core.thread;

import com.gls.job.admin.web.service.JobRegistryService;
import com.gls.job.core.base.BaseThread;
import com.gls.job.core.constants.JobConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author george
 */
@Slf4j
@Component
public class JobRegistryThread extends BaseThread {
    @Resource
    private JobRegistryService jobRegistryService;

    @Override
    protected void destroyExecute() {
        log.info(">>>>>>>>>>> gls-job, JobRegistryThread destroy");
    }

    @Override
    protected void doExecute() {
        jobRegistryService.doJobRegistry();
    }

    @Override
    protected void initExecute() {
        log.info(">>>>>>>>>>> gls-job, JobRegistryThread init");
    }

    @Override
    protected void sleepExecute() {
        try {
            TimeUnit.SECONDS.sleep(JobConstants.BEAT_TIMEOUT);
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
        }
    }
}
