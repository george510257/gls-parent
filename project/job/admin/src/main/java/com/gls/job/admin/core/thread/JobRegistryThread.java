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
    protected void initExecute() throws Exception {
        log.info(">>>>>>>>>>> gls-job, JobRegistryThread init");
    }

    @Override
    protected void doExecute() throws Exception {
        jobRegistryService.doJobRegistry();
    }

    @Override
    protected void sleepExecute() throws Exception {
        TimeUnit.SECONDS.sleep(JobConstants.BEAT_TIMEOUT);
    }

    @Override
    protected void destroyExecute() throws Exception {
        log.info(">>>>>>>>>>> gls-job, JobRegistryThread destroy");
    }
}
