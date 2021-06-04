package com.gls.job.core.api.rpc;

import com.gls.job.core.api.model.CallbackModel;
import com.gls.job.core.api.model.RegistryModel;
import com.gls.job.core.api.model.Result;

import java.util.List;

/**
 * @author xuxueli 2017-07-27 21:52:49
 */
public interface AdminApi {

    // ---------------------- callback ----------------------

    /**
     * callback
     *
     * @param callbackParamList
     * @return
     */
    public Result<String> callback(List<CallbackModel> callbackParamList);

    // ---------------------- registry ----------------------

    /**
     * registry
     *
     * @param registryModel
     * @return
     */
    public Result<String> registry(RegistryModel registryModel);

    /**
     * registry remove
     *
     * @param registryModel
     * @return
     */
    public Result<String> registryRemove(RegistryModel registryModel);

    // ---------------------- biz (custome) ----------------------
    // group、job ... manage

}
