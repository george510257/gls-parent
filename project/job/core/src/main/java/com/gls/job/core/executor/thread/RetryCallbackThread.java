package com.gls.job.core.executor.thread;

import com.gls.job.core.common.base.BaseThread;
import com.gls.job.core.common.constants.JobConstants;
import com.gls.job.core.executor.web.service.CallbackService;
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
    protected void initExecute() throws Exception {

    }

    @Override
    protected void doExecute() throws Exception {
        callbackService.retryCallback();
    }

    @Override
    protected void sleepExecute() throws Exception {
        TimeUnit.SECONDS.sleep(JobConstants.BEAT_TIMEOUT);
    }

    @Override
    protected void destroyExecute() throws Exception {
        log.info(">>>>>>>>>>> xxl-job, executor retry callback thread destroy.");
    }
}
