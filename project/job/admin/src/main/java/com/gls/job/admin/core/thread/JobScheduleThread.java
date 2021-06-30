package com.gls.job.admin.core.thread;

import com.gls.job.admin.web.service.JobInfoService;
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
    private JobInfoService jobInfoService;
    private boolean preReadSuc;
    private long start;

    @Override
    protected void destroyExecute() {
    }

    @Override
    protected void doExecute() {
        start = System.currentTimeMillis();
        preReadSuc = jobInfoService.doJobSchedule();
    }

    @Override
    protected void initExecute() {
        try {
            TimeUnit.MILLISECONDS.sleep(5000 - System.currentTimeMillis() % 1000);
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
        }
    }

    @Override
    protected void sleepExecute() {
        long cost = System.currentTimeMillis() - start;
        // Wait seconds, align second
        // scan-overtime, not wait
        if (cost < 1000) {
            // pre-read period: success > scan each second; fail > skip this period;
            try {
                TimeUnit.MILLISECONDS.sleep((preReadSuc ? 1000 : JobConstants.PRE_READ_MS) - System.currentTimeMillis() % 1000);
            } catch (InterruptedException e) {
                log.error(e.getMessage(), e);
            }
        }
    }
}
