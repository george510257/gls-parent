package com.gls.job.admin.web.service;

import com.gls.job.core.api.model.CallbackModel;

import java.util.List;

/**
 * @author george
 */
public interface JobLogService {

    /**
     * callback
     *
     * @param callbackModels
     */
    void callback(List<CallbackModel> callbackModels);

    /**
     * do Job Complete
     */
    void doJobComplete();

    /**
     * do Job Fail
     */
    void doJobFail();

    /**
     * do Job LogReport
     *
     * @param lastCleanLogTime
     * @return
     */
    long doJobLogReport(long lastCleanLogTime);
}
