package com.gls.job.core.api.rpc;

import com.gls.job.core.api.model.CallbackModel;
import com.gls.job.core.api.model.RegistryModel;
import com.gls.job.core.api.model.Result;

import java.util.List;

/**
 * @author xuxueli 2017-07-27 21:52:49
 */
public interface AdminBiz {

    // ---------------------- callback ----------------------

    /**
     * callback
     *
     * @param callbackModelList
     * @return
     */
    public Result<String> callback(List<CallbackModel> callbackModelList);

    // ---------------------- registry ----------------------

    /**
     * registry
     *
     * @param registryParam
     * @return
     */
    public Result<String> registry(RegistryModel registryParam);

    /**
     * registry remove
     *
     * @param registryParam
     * @return
     */
    public Result<String> registryRemove(RegistryModel registryParam);

    // ---------------------- biz (custome) ----------------------
    // group、job ... manage

}
