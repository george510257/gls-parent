package com.gls.job.admin.web.service;

import com.gls.job.core.constants.TriggerType;

import java.util.List;

/**
 * @author george
 */
public interface JobTriggerService {
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
}
