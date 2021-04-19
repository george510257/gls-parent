package com.gls.job.executor.server;

import com.gls.job.executor.config.XxlJobExecutor;
import com.gls.job.executor.thread.CallbackThread;
import com.gls.job.executor.thread.RetryCallbackThread;
import com.gls.job.executor.web.service.CallbackService;
import lombok.extern.slf4j.Slf4j;

/**
 * @author georg
 */
@Slf4j
public class CallbackServer {

    private static final CallbackServer INSTANCE = new CallbackServer();

    /**
     * callback thread
     */
    private CallbackThread triggerCallbackThread;
    private RetryCallbackThread triggerRetryCallbackThread;

    public static CallbackServer getInstance() {
        return INSTANCE;
    }

    public void start(CallbackService callbackService) {

        // valid
        if (XxlJobExecutor.getAdminApiList() == null) {
            log.warn(">>>>>>>>>>> gls-job, executor callback config fail, adminAddresses is null.");
            return;
        }

        // callback
        triggerCallbackThread = new CallbackThread(callbackService);
        triggerCallbackThread.setDaemon(true);
        triggerCallbackThread.setName("gls-job, executor TriggerCallbackThread");
        triggerCallbackThread.start();

        // retry
        triggerRetryCallbackThread = new RetryCallbackThread(callbackService);
        triggerRetryCallbackThread.setDaemon(true);
        triggerRetryCallbackThread.start();

    }

    public void toStop() {
        triggerCallbackThread.setToStop(true);
        // stop callback, interrupt and wait
        if (triggerCallbackThread != null) {
            // support empty admin address
            triggerCallbackThread.interrupt();
            try {
                triggerCallbackThread.join();
            } catch (InterruptedException e) {
                log.error(e.getMessage(), e);
            }
        }

        triggerRetryCallbackThread.setToStop(true);
        // stop retry, interrupt and wait
        if (triggerRetryCallbackThread != null) {
            triggerRetryCallbackThread.interrupt();
            try {
                triggerRetryCallbackThread.join();
            } catch (InterruptedException e) {
                log.error(e.getMessage(), e);
            }
        }

    }

}
