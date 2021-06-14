package com.gls.job.admin.web.rpc;

import com.gls.job.admin.web.service.JobCompleterService;
import com.gls.job.core.api.model.CallbackModel;
import com.gls.job.core.api.model.RegistryModel;
import com.gls.job.core.api.model.Result;
import com.gls.job.core.api.rpc.AdminApi;
import com.xxl.job.admin.core.thread.JobRegistryHelper;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author xuxueli 2017-07-27 21:54:20
 */
@DubboService
public class AdminRpcService implements AdminApi {

    @Resource
    private JobCompleterService jobCompleterService;

    @Override
    public Result<String> callback(List<CallbackModel> callbackParamList) {
        return jobCompleterService.callback(callbackParamList);
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
