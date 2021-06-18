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
    protected void initExecute() throws Exception {
        log.info(">>>>>>>>>>> gls-job, JobFailThread init");
    }

    @Override
    protected void doExecute() throws Exception {
        jobLogService.doJobFail();
    }

    @Override
    protected void sleepExecute() throws Exception {
        TimeUnit.SECONDS.sleep(10);
    }

    @Override
    protected void destroyExecute() throws Exception {
        log.info(">>>>>>>>>>> gls-job, JobFailThread destroy");
    }
}
