package com.gls.job.admin.web.service;

import com.gls.job.admin.web.model.JobLog;
import com.gls.job.core.api.model.CallbackModel;

import java.util.List;

/**
 * @author xuxueli 2020-10-30 20:43:10
 */

public interface JobLogService {

    /**
     * update HandleInfo And Finish
     *
     * @param jobLog
     */
    void updateHandleInfoAndFinish(JobLog jobLog);

    /**
     * callback
     *
     * @param callbackModels
     * @return
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
}
