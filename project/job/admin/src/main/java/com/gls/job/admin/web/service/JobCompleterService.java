package com.gls.job.admin.web.service;

import com.gls.job.admin.web.model.JobLog;
import com.gls.job.core.api.model.CallbackModel;
import com.gls.job.core.api.model.Result;

import java.util.List;

/**
 * @author xuxueli 2020-10-30 20:43:10
 */

public interface JobCompleterService {

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
    Result<String> callback(List<CallbackModel> callbackModels);
}
