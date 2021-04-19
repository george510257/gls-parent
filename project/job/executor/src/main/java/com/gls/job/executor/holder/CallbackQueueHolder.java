package com.gls.job.executor.holder;

import com.gls.job.core.api.model.CallbackModel;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author george
 */
@Slf4j
public class CallbackQueueHolder {
    private static final CallbackQueueHolder INSTANCE = new CallbackQueueHolder();
    private final LinkedBlockingQueue<CallbackModel> callBackQueue = new LinkedBlockingQueue<>();

    private CallbackQueueHolder() {
    }

    public static CallbackQueueHolder getInstance() {
        return INSTANCE;
    }

    public void pushCallback(CallbackModel callback) {
        callBackQueue.add(callback);
        log.debug(">>>>>>>>>>> gls-job, push callback request, logId:{}", callback.getLogId());
    }

    public CallbackModel take() throws InterruptedException {
        return callBackQueue.take();
    }

    public int drainTo(Collection<CallbackModel> callbackModels) {
        return callBackQueue.drainTo(callbackModels);
    }
}
