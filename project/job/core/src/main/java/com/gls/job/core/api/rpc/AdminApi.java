package com.gls.job.core.api.rpc;

import com.gls.framework.api.result.Result;
import com.gls.job.core.api.model.CallbackModel;
import com.gls.job.core.api.model.RegistryModel;

import java.util.List;

/**
 * @author xuxueli 2017-07-27 21:52:49
 */
public interface AdminApi {
    /**
     * callback
     *
     * @param callbackModelList
     * @return
     */
    Result<String> callback(List<CallbackModel> callbackModelList);

    /**
     * registry
     *
     * @param registryModel
     * @return
     */
    Result<String> registry(RegistryModel registryModel);

    /**
     * registry remove
     *
     * @param registryModel
     * @return
     */
    Result<String> registryRemove(RegistryModel registryModel);
}
