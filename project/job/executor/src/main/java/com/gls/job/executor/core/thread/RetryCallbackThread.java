package com.gls.job.executor.core.thread;

import com.gls.job.core.base.thread.BaseThread;
import com.gls.job.core.constants.JobConstants;
import com.gls.job.executor.web.service.CallbackService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author george
 */
@Slf4j
@Component
public class RetryCallbackThread extends BaseThread {

    @Resource
    private CallbackService callbackService;

    @Override
    protected void initExecute() {

    }

    @Override
    protected void doExecute() {
        callbackService.retryFailCallbackFile();
    }

    @Override
    protected void sleepExecute() throws Exception {
        TimeUnit.SECONDS.sleep(JobConstants.BEAT_TIMEOUT);
    }

    @Override
    protected void destroyExecute() {
        log.info(">>>>>>>>>>> gls-job, executor retry callback thread destroy.");
    }

}
