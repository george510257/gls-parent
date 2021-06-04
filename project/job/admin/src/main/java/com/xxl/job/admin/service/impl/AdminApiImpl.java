package com.xxl.job.admin.service.impl;

import com.gls.job.core.api.model.CallbackModel;
import com.gls.job.core.api.model.RegistryModel;
import com.gls.job.core.api.model.Result;
import com.gls.job.core.api.rpc.AdminApi;
import com.xxl.job.admin.core.thread.JobCompleteHelper;
import com.xxl.job.admin.core.thread.JobRegistryHelper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author xuxueli 2017-07-27 21:54:20
 */
@Service
public class AdminApiImpl implements AdminApi {

    @Override
    public Result<String> callback(List<CallbackModel> callbackParamList) {
        return JobCompleteHelper.getInstance().callback(callbackParamList);
    }

    @Override
    public Result<String> registry(RegistryModel registryModel) {
        return JobRegistryHelper.getInstance().registry(registryModel);
    }

    @Override
    public Result<String> registryRemove(RegistryModel registryModel) {
        return JobRegistryHelper.getInstance().registryRemove(registryModel);
    }

}
