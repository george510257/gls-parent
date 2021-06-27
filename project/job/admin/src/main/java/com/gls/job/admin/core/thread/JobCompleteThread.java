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
    protected void initExecute() throws Exception {
        TimeUnit.MILLISECONDS.sleep(50);
    }

    @Override
    protected void doExecute() throws Exception {
        jobLogService.doJobComplete();
    }

    @Override
    protected void sleepExecute() throws Exception {
        TimeUnit.SECONDS.sleep(60);
    }

    @Override
    protected void destroyExecute() throws Exception {
        log.info(">>>>>>>>>>> gls-job, JobCompleteThread destroy");
    }
}
