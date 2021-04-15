package com.gls.job.admin.service.impl;

import com.gls.job.admin.core.thread.JobCompleteHelper;
import com.gls.job.admin.core.thread.JobRegistryHelper;
import com.gls.job.core.biz.AdminBiz;
import com.gls.job.core.biz.model.HandleCallbackParam;
import com.gls.job.core.biz.model.RegistryParam;
import com.gls.job.core.biz.model.ReturnT;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author xuxueli 2017-07-27 21:54:20
 */
@Service
public class AdminBizImpl implements AdminBiz {

    @Override
    public ReturnT<String> callback(List<HandleCallbackParam> callbackParamList) {
        return JobCompleteHelper.getInstance().callback(callbackParamList);
    }

    @Override
    public ReturnT<String> registry(RegistryParam registryParam) {
        return JobRegistryHelper.getInstance().registry(registryParam);
    }

    @Override
    public ReturnT<String> registryRemove(RegistryParam registryParam) {
        return JobRegistryHelper.getInstance().registryRemove(registryParam);
    }

}
