package com.gls.job.core.api.rpc;

import com.gls.job.core.api.model.CallbackModel;
import com.gls.job.core.api.model.RegistryModel;
import com.gls.job.core.api.model.Result;

import java.util.List;

/**
 * @author george
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
