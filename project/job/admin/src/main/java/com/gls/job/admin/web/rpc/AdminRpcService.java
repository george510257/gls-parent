package com.gls.job.admin.web.rpc;

import com.gls.framework.api.result.Result;
import com.gls.job.admin.web.service.JobAsyncService;
import com.gls.job.core.api.model.CallbackModel;
import com.gls.job.core.api.model.RegistryModel;
import com.gls.job.core.api.rpc.AdminApi;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author xuxueli 2017-07-27 21:54:20
 */
@DubboService
public class AdminRpcService implements AdminApi {

    @Resource
    private JobAsyncService jobAsyncService;

    @Override
    public Result<String> callback(List<CallbackModel> callbackModels) {
        jobAsyncService.asyncCallback(callbackModels);
        return Result.SUCCESS;
    }

    @Override
    public Result<String> registry(RegistryModel registryModel) {
        jobAsyncService.asyncRegistry(registryModel);
        return Result.SUCCESS;
    }

    @Override
    public Result<String> registryRemove(RegistryModel registryModel) {
        jobAsyncService.asyncRegistryRemove(registryModel);
        return Result.SUCCESS;
    }

}
