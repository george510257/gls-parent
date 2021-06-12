package com.gls.job.core.executor.thread;

import com.gls.job.core.common.base.BaseThread;
import com.gls.job.core.executor.constants.JobExecutorProperties;
import com.gls.job.core.executor.web.service.JobLogService;
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
    @Resource
    private JobExecutorProperties jobExecutorProperties;

    @Override
    protected void initExecute() throws Exception {

    }

    @Override
    protected void doExecute() throws Exception {
        jobLogService.logFileClean(jobExecutorProperties.getLogRetentionDays());
    }

    @Override
    protected void sleepExecute() throws Exception {
        TimeUnit.DAYS.sleep(1);
    }

    @Override
    protected void destroyExecute() throws Exception {
        log.info(">>>>>>>>>>> xxl-job, executor JobLogCleanThread thread destroy.");
    }
}
