package com.gls.job.executor.core.thread;

import com.gls.job.core.base.BaseThread;
import com.gls.job.executor.web.service.JobLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author george
 */
@Slf4j
@Component
public class JobLogCleanThread extends BaseThread {
    @Resource
    private JobLogService jobLogService;

    @Override
    protected void initExecute() throws Exception {
        log.info(">>>>>>>>>>> gls-job, executor JobLogCleanThread thread init.");
    }

    @Override
    protected void doExecute() throws Exception {
        log.info(">>>>>>>>>>> gls-job, executor JobLogCleanThread thread doExecute.");
        jobLogService.logFileClean();
    }

    @Override
    protected void sleepExecute() throws Exception {
        log.info(">>>>>>>>>>> gls-job, executor JobLogCleanThread thread sleep.");
        TimeUnit.DAYS.sleep(1);
    }

    @Override
    protected void destroyExecute() throws Exception {
        log.info(">>>>>>>>>>> gls-job, executor JobLogCleanThread thread destroy.");
    }
}
