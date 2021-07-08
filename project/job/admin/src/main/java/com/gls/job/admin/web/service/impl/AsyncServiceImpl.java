package com.gls.job.admin.web.service.impl;

import com.gls.job.admin.web.service.AsyncService;
import com.gls.job.admin.web.service.JobCompleteService;
import com.gls.job.admin.web.service.JobRegistryService;
import com.gls.job.admin.web.service.JobTriggerService;
import com.gls.job.core.api.model.CallbackModel;
import com.gls.job.core.api.model.RegistryModel;
import com.gls.job.core.constants.TriggerType;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author george
 */
@Service("asyncService")
public class AsyncServiceImpl implements AsyncService {
    @Resource
    private JobTriggerService jobTriggerService;
    @Resource
    private JobCompleteService jobCompleteService;
    @Resource
    private JobRegistryService jobRegistryService;

    @Override
    public void asyncCallback(List<CallbackModel> callbackModels) {
        jobCompleteService.callback(callbackModels);
    }

    @Override
    public void asyncRegistry(RegistryModel registryModel) {
        jobRegistryService.add(registryModel);
    }

    @Override
    public void asyncRegistryRemove(RegistryModel registryModel) {
        jobRegistryService.remove(registryModel);
    }

    @Override
    public void asyncTrigger(Long jobId, TriggerType triggerType, int failRetryCount, String executorShardingParam, String executorParam, List<String> addressList) {
        jobTriggerService.trigger(jobId, triggerType, failRetryCount, executorShardingParam, executorParam, addressList);
    }
}
