package com.gls.job.admin.web.service;

import com.gls.job.admin.constants.TriggerType;
import com.gls.job.admin.web.model.JobInfo;
import com.gls.job.admin.web.model.query.QueryJobInfo;
import com.gls.starter.data.jpa.base.BaseService;

import java.util.List;

/**
 * @author george
 */
public interface JobInfoService extends BaseService<JobInfo, QueryJobInfo> {
    /**
     * do JobSchedule
     *
     * @return
     */
    boolean doJobSchedule();

    /**
     * do JobSchedule Ring
     */
    void doJobScheduleRing();

    /**
     * startJobInfo
     *
     * @param jobInfoId
     */
    void start(Long jobInfoId);

    /**
     * stop
     *
     * @param jobInfoId
     */
    void stop(Long jobInfoId);

    /**
     * getByJobGroupId
     *
     * @param jobGroupId
     * @return
     */
    List<JobInfo> getByJobGroupId(Long jobGroupId);

    /**
     * trigger
     *
     * @param jobId
     * @param triggerType
     * @param failRetryCount
     * @param executorShardingParam
     * @param executorParam
     * @param addressList
     */
    void trigger(Long jobId, TriggerType triggerType, int failRetryCount, String executorShardingParam, String executorParam, List<String> addressList);

    /**
     * nextTriggerTime
     *
     * @param scheduleType
     * @param scheduleConf
     * @return
     */
    List<String> nextTriggerTime(String scheduleType, String scheduleConf);
}
