package com.gls.job.admin.web.service;

import com.gls.job.admin.web.model.JobLog;
import com.gls.job.admin.web.model.query.QueryJobLog;
import com.gls.job.core.api.model.CallbackModel;
import com.gls.job.core.api.model.LogModel;
import com.gls.job.core.api.model.LogResultModel;
import com.gls.starter.data.jpa.base.BaseService;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author george
 */
public interface JobLogService extends BaseService<JobLog, QueryJobLog> {
    /**
     * callback
     *
     * @param callbackModels
     */
    void callback(List<CallbackModel> callbackModels);

    /**
     * clearLog
     *
     * @param groupId
     * @param jobId
     * @param type
     */
    void clearLog(Long groupId, Long jobId, Integer type);

    /**
     * do Job Complete
     */
    void doJobComplete();

    /**
     * do Job Fail
     */
    void doJobFail();

    /**
     * getIndexMap
     *
     * @param jobId
     * @return
     */
    Map<String, Object> getIndexMap(Long jobId);

    /**
     * logDetailCat
     *
     * @param logModel
     * @return
     */
    LogResultModel logDetailCat(LogModel logModel);

    /**
     * logKill
     *
     * @param logId
     */
    void logKill(Long logId);

    /**
     * getLogReport
     *
     * @param todayFrom
     * @param todayTo
     * @return
     */
    Map<String, Long> getLogReport(Date todayFrom, Date todayTo);
}
