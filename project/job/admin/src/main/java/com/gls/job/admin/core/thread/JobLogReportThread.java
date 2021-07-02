package com.gls.job.admin.core.thread;

import com.gls.job.admin.web.service.JobLogReportService;
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
    private JobLogReportService jobLogReportService;
    private long lastCleanLogTime;

    @Override
    protected void destroyExecute() {
        log.info(">>>>>>>>>>> gls-job, job log report thread stop");
    }

    @Override
    protected void doExecute() {
        lastCleanLogTime = jobLogReportService.doJobLogReport(lastCleanLogTime);
    }

    @Override
    protected void initExecute() {
        lastCleanLogTime = 0;
    }

    @Override
    protected void sleepExecute() throws Exception {
        TimeUnit.MINUTES.sleep(1);
    }
}
