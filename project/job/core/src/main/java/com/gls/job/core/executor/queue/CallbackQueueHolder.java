package com.gls.job.core.executor.queue;

import com.gls.job.core.api.model.CallbackModel;
import com.gls.job.core.common.base.BaseQueueHolder;

/**
 * @author george
 */
public class CallbackQueueHolder extends BaseQueueHolder<CallbackModel> {

    private static final CallbackQueueHolder INSTANCE = new CallbackQueueHolder();

    private CallbackQueueHolder() {
    }

    public static CallbackQueueHolder getInstance() {
        return INSTANCE;
    }
}
