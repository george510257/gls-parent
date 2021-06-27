package com.gls.job.admin.web.service;

import com.gls.job.admin.constants.TriggerType;
import com.gls.job.core.api.model.CallbackModel;
import com.gls.job.core.api.model.RegistryModel;
import org.springframework.scheduling.annotation.Async;

import java.util.List;

/**
 * @author george
 */
@Async
public interface JobAsyncService {
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
    void asyncTrigger(Long jobId, TriggerType triggerType, int failRetryCount, String executorShardingParam, String executorParam, List<String> addressList);

    /**
     * callback
     *
     * @param callbackModels
     */
    void asyncCallback(List<CallbackModel> callbackModels);

    /**
     * registry
     *
     * @param registryModel
     */
    void asyncRegistry(RegistryModel registryModel);

    /**
     * registryRemove
     *
     * @param registryModel
     */
    void asyncRegistryRemove(RegistryModel registryModel);
}
