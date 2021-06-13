package com.gls.job.admin.web.service;

import com.gls.job.admin.core.enums.TriggerType;

/**
 * gls-job trigger
 *
 * @author xuxueli
 * @date 17/7/13
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
    void trigger(Long jobId, TriggerType triggerType, int failRetryCount, String executorShardingParam, String executorParam, String addressList);
}
