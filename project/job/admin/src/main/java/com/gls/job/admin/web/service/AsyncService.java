package com.gls.job.admin.web.service;

import com.gls.job.core.api.model.CallbackModel;
import com.gls.job.core.api.model.RegistryModel;
import com.gls.job.core.constants.TriggerType;
import org.springframework.scheduling.annotation.Async;

import java.util.List;

/**
 * @author george
 */
@Async
public interface AsyncService {
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
}
