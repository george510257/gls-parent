package com.gls.job.admin.web.service;

import com.gls.job.admin.constants.TriggerType;
import com.gls.job.admin.web.model.JobGroup;
import com.gls.job.admin.web.model.JobInfo;
import com.gls.job.admin.web.model.query.QueryJobInfo;
import com.gls.starter.data.jpa.base.BaseService;

import java.util.List;
import java.util.Map;

/**
 * @author george
 */
public interface JobInfoService extends BaseService<JobInfo, QueryJobInfo> {
    /**
     * do JobSchedule Ring
     */
    void doJobScheduleRing();

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
     * do JobSchedule
     *
     * @return
     */
    boolean doJobSchedule();

    /**
     * getIndexData
     *
     * @param jobGroupId
     * @return
     */
    Map<String, Object> getIndexData(Long jobGroupId);

    /**
     * stopJobInfo
     *
     * @param jobInfoId
     */
    void stopJobInfo(Long jobInfoId);

    /**
     * startJobInfo
     *
     * @param jobInfoId
     */
    void startJobInfo(Long jobInfoId);

    /**
     * nextTriggerTime
     *
     * @param scheduleType
     * @param scheduleConf
     * @return
     */
    List<String> nextTriggerTime(String scheduleType, String scheduleConf);

    /**
     * getJobGroupListByRole
     *
     * @param allList
     * @return
     */
    List<JobGroup> getJobGroupListByRole(List<JobGroup> allList);
}
