package com.gls.job.admin.web.service.impl;

import com.gls.job.admin.core.server.JobCompleteServer;
import com.gls.job.admin.core.server.JobRegistryHelper;
import com.gls.job.core.api.model.CallbackModel;
import com.gls.job.core.api.model.RegistryModel;
import com.gls.job.core.api.model.Result;
import com.gls.job.core.api.rpc.AdminApi;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author george 2017-07-27 21:54:20
 */
@Service
public class AdminApiImpl implements AdminApi {

    @Override
    public Result<String> callback(List<CallbackModel> callbackModelList) {
        return JobCompleteServer.getInstance().callback(callbackModelList);
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
