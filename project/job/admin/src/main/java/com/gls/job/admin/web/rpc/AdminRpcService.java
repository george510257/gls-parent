package com.gls.job.admin.web.rpc;

import com.gls.job.admin.web.service.JobLogService;
import com.gls.job.admin.web.service.JobRegistryService;
import com.gls.job.core.api.model.CallbackModel;
import com.gls.job.core.api.model.RegistryModel;
import com.gls.job.core.api.model.Result;
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
    private JobLogService jobLogService;

    @Resource
    private JobRegistryService jobRegistryService;

    @Override
    public Result<String> callback(List<CallbackModel> callbackParamList) {
        jobLogService.callback(callbackParamList);
        return Result.SUCCESS;
    }

    @Override
    public Result<String> registry(RegistryModel registryModel) {
        jobRegistryService.registry(registryModel);
        return Result.SUCCESS;
    }

    @Override
    public Result<String> registryRemove(RegistryModel registryModel) {
        jobRegistryService.registryRemove(registryModel);
        return Result.SUCCESS;
    }

}
