package com.gls.job.admin.core.thread;

import com.gls.job.admin.web.service.JobCompleteService;
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
    private JobCompleteService jobCompleteService;

    @Override
    protected void destroyExecute() throws Exception {
        log.info(">>>>>>>>>>> gls-job, JobCompleteThread destroy");
    }

    @Override
    protected void doExecute() throws Exception {
        jobCompleteService.run();
    }

    @Override
    protected void initExecute() throws Exception {
        TimeUnit.MILLISECONDS.sleep(50);
    }

    @Override
    protected void sleepExecute() throws Exception {
        TimeUnit.SECONDS.sleep(60);
    }
}
