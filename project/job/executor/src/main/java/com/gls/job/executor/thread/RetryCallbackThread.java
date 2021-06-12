package com.gls.job.executor.thread;

import com.gls.job.core.common.base.BaseThread;
import com.gls.job.core.common.constants.JobConstants;
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
    protected void initExecute() throws Exception {
        log.info(">>>>>>>>>>> xxl-job, executor RetryCallbackThread thread init.");
    }

    @Override
    protected void doExecute() throws Exception {
        log.info(">>>>>>>>>>> xxl-job, executor RetryCallbackThread thread doExecute.");
        callbackService.retryCallback();
    }

    @Override
    protected void sleepExecute() throws Exception {
        log.info(">>>>>>>>>>> xxl-job, executor RetryCallbackThread thread sleep.");
        TimeUnit.SECONDS.sleep(JobConstants.BEAT_TIMEOUT);
    }

    @Override
    protected void destroyExecute() throws Exception {
        log.info(">>>>>>>>>>> xxl-job, executor RetryCallbackThread thread destroy.");
    }
}
