package com.gls.job.executor.core.thread;

import com.gls.job.core.base.thread.BaseThread;
import com.gls.job.executor.web.service.LogFileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author george
 */
@Slf4j
@Component
public class LogFileCleanThread extends BaseThread {

    @Resource
    private LogFileService logFileService;

    @Override
    protected void initExecute() {
    }

    @Override
    protected void doExecute() {
        logFileService.cleanLogFile();
    }

    @Override
    protected void sleepExecute() throws Exception {
        TimeUnit.DAYS.sleep(1);
    }

    @Override
    protected void destroyExecute() {
        log.info(">>>>>>>>>>> gls-job, executor JobLogFileCleanThread thread destroy.");
    }
}
