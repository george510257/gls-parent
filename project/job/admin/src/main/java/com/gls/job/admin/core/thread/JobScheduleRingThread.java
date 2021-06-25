package com.gls.job.admin.core.thread;

import com.gls.job.admin.web.service.JobInfoService;
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
public class JobScheduleRingThread extends BaseThread {

    @Resource
    private JobInfoService jobInfoService;

    @Override
    protected void initExecute() throws Exception {

    }

    @Override
    protected void doExecute() throws Exception {
        jobInfoService.doJobScheduleRing();
    }

    @Override
    protected void sleepExecute() throws Exception {
        TimeUnit.MILLISECONDS.sleep(1000 - System.currentTimeMillis() % 1000);
    }

    @Override
    protected void destroyExecute() throws Exception {
        log.info(">>>>>>>>>>> gls-job, JobScheduleHelper#ringThread stop");
    }

}
