package com.gls.job.admin.core.thread;

import com.gls.job.admin.web.service.JobScheduleService;
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
public class JobScheduleThread extends BaseThread {
    @Resource
    private JobScheduleService jobScheduleService;
    private boolean preReadSuc;
    private long start;

    @Override
    protected void destroyExecute() {
    }

    @Override
    protected void doExecute() {
        start = System.currentTimeMillis();
        preReadSuc = jobScheduleService.run();
    }

    @Override
    protected void initExecute() throws Exception {
        TimeUnit.MILLISECONDS.sleep(5000 - System.currentTimeMillis() % 1000);
    }

    @Override
    protected void sleepExecute() throws Exception {
        long cost = System.currentTimeMillis() - start;
        // Wait seconds, align second
        // scan-overtime, not wait
        if (cost < 1000) {
            // pre-read period: success > scan each second; fail > skip this period;
            TimeUnit.MILLISECONDS.sleep((preReadSuc ? 1000 : JobConstants.PRE_READ_MS) - System.currentTimeMillis() % 1000);
        }
    }
}
