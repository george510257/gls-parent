package com.gls.job.admin.web.service.impl;

import com.gls.job.admin.constants.TriggerType;
import com.gls.job.admin.web.service.JobAsyncService;
import com.gls.job.admin.web.service.JobInfoService;
import com.gls.job.admin.web.service.JobLogService;
import com.gls.job.admin.web.service.JobRegistryService;
import com.gls.job.core.api.model.CallbackModel;
import com.gls.job.core.api.model.RegistryModel;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author george
 */
@Service("jobAsyncService")
public class JobAsyncServiceImpl implements JobAsyncService {
    @Resource
    private JobInfoService jobInfoService;
    @Resource
    private JobLogService jobLogService;
    @Resource
    private JobRegistryService jobRegistryService;

    @Override
    public void asyncTrigger(Long jobId, TriggerType triggerType, int failRetryCount, String executorShardingParam, String executorParam, List<String> addressList) {
        jobInfoService.trigger(jobId, triggerType, failRetryCount, executorShardingParam, executorParam, addressList);
    }

    @Override
    public void asyncCallback(List<CallbackModel> callbackModels) {
        jobLogService.callback(callbackModels);
    }

    @Override
    public void asyncRegistry(RegistryModel registryModel) {
        jobRegistryService.add(registryModel);
    }

    @Override
    public void asyncRegistryRemove(RegistryModel registryModel) {
        jobRegistryService.remove(registryModel);
    }
}
