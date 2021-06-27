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
public class JobLogReportThread extends BaseThread {
    @Resource
    private JobLogService jobLogService;
    private long lastCleanLogTime;

    @Override
    protected void initExecute() throws Exception {
        lastCleanLogTime = 0;
    }

    @Override
    protected void doExecute() throws Exception {
        lastCleanLogTime = jobLogService.doJobLogReport(lastCleanLogTime);
    }

    @Override
    protected void sleepExecute() throws Exception {
        TimeUnit.MINUTES.sleep(1);
    }

    @Override
    protected void destroyExecute() throws Exception {
        log.info(">>>>>>>>>>> gls-job, job log report thread stop");
    }
}
