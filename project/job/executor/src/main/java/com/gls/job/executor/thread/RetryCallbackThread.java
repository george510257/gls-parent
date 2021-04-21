package com.gls.job.executor.thread;

import com.gls.job.core.constants.JobConstants;
import com.gls.job.executor.web.service.CallbackService;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @author george
 */
@Slf4j
public class RetryCallbackThread extends Thread {

    private final CallbackService callbackService;

    @Setter
    private volatile boolean toStop = false;

    public RetryCallbackThread(CallbackService callbackService) {
        this.callbackService = callbackService;
    }

    @Override
    public void run() {
        while (!toStop) {
            try {
                retryFailCallbackFile();
            } catch (Exception e) {
                if (!toStop) {
                    log.error(e.getMessage(), e);
                }

            }
            try {
                TimeUnit.SECONDS.sleep(JobConstants.BEAT_TIMEOUT);
            } catch (InterruptedException e) {
                if (!toStop) {
                    log.error(e.getMessage(), e);
                }
            }
        }
        log.info(">>>>>>>>>>> gls-job, executor retry callback thread destroy.");
    }

    private void retryFailCallbackFile() {
        callbackService.retryFailCallbackFile();
    }
}
