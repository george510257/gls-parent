package com.gls.job.admin.web.service;

import com.gls.job.admin.web.model.JobInfo;
import com.gls.job.admin.web.model.JobLog;
import com.gls.job.admin.web.model.query.QueryJobLog;
import com.gls.job.core.api.model.CallbackModel;
import com.gls.job.core.api.model.LogResultModel;
import com.gls.starter.data.jpa.base.BaseService;

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

    /**
     * getIndexMap
     *
     * @param jobId
     * @return
     */
    Map<String, Object> getIndexMap(Long jobId);

    /**
     * getJobsByGroup
     *
     * @param jobGroupId
     * @return
     */
    List<JobInfo> getJobsByGroup(Long jobGroupId);

    /**
     * logDetail
     *
     * @param logId
     * @return
     */
    JobLog logDetail(Long logId);

    /**
     * logDetailCat
     *
     * @param executorAddress
     * @param triggerTime
     * @param logId
     * @param fromLineNum
     * @return
     */
    LogResultModel logDetailCat(String executorAddress, Long triggerTime, Long logId, Integer fromLineNum);

    /**
     * logKill
     *
     * @param logId
     */
    void logKill(Long logId);

    /**
     * clearLog
     *
     * @param groupId
     * @param jobId
     * @param type
     */
    void clearLog(Long groupId, Long jobId, Integer type);
}
