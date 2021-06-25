package com.gls.job.admin.web.service;

import com.gls.job.admin.core.enums.TriggerType;
import com.gls.job.admin.web.model.JobInfo;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

/**
 * @author george
 */
public interface JobInfoService {

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
     * @throws ParseException
     */
    boolean doJobSchedule() throws ParseException;

    /**
     * getIndexData
     *
     * @param jobGroupId
     * @return
     */
    Map<String, Object> getIndexData(Long jobGroupId);

    /**
     * pageList
     *
     * @param jobGroup
     * @param triggerStatus
     * @param jobDesc
     * @param executorHandler
     * @param author
     * @param start
     * @param length
     * @return
     */
    Map<String, Object> pageList(Long jobGroup, Boolean triggerStatus, String jobDesc, String executorHandler, String author, int start, int length);

    /**
     * addJobInfo
     *
     * @param jobInfo
     */
    void addJobInfo(JobInfo jobInfo);

    /**
     * updateJobInfo
     *
     * @param jobInfo
     */
    void updateJobInfo(JobInfo jobInfo);

    /**
     * removeJobInfo
     *
     * @param jobInfoId
     */
    void removeJobInfo(Long jobInfoId);

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
}
