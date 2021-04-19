package com.gls.job.executor.web.service;

import com.gls.job.core.api.model.CallbackModel;

import java.util.List;

/**
 * @author georg
 */
public interface CallbackService {

    /**
     * doCallback
     *
     * @param callbackModelList
     */
    void doCallback(List<CallbackModel> callbackModelList);

    /**
     * retryFailCallbackFile
     */
    void retryFailCallbackFile();
}
