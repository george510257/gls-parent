package com.gls.job.admin.web.rpc;

import com.gls.framework.api.result.Result;
import com.gls.job.admin.web.service.AsyncService;
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
    private AsyncService asyncService;

    @Override
    public Result<String> callback(List<CallbackModel> callbackModels) {
        asyncService.asyncCallback(callbackModels);
        return Result.SUCCESS;
    }

    @Override
    public Result<String> registry(RegistryModel registryModel) {
        asyncService.asyncRegistry(registryModel);
        return Result.SUCCESS;
    }

    @Override
    public Result<String> registryRemove(RegistryModel registryModel) {
        asyncService.asyncRegistryRemove(registryModel);
        return Result.SUCCESS;
    }
}
