package com.gls.job.admin.web.service;

import com.gls.job.core.api.model.CallbackModel;

import java.util.List;

/**
 * @author george
 */
public interface JobCompleteService {
    /**
     * run
     */
    void run();

    /**
     * callback
     *
     * @param callbackModels
     */
    void callback(List<CallbackModel> callbackModels);
}
