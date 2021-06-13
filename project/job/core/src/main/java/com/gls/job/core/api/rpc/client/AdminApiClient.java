package com.gls.job.core.api.rpc.client;

import com.gls.job.core.api.model.CallbackModel;
import com.gls.job.core.api.model.RegistryModel;
import com.gls.job.core.api.model.Result;
import com.gls.job.core.api.rpc.AdminApi;
import com.gls.job.core.util.JobRemotingUtil;

import java.util.List;

/**
 * admin api test
 *
 * @author xuxueli 2017-07-28 22:14:52
 */
public class AdminApiClient implements AdminApi {

    private final int timeout = 3;
    private String addressUrl;
    private String accessToken;

    public AdminApiClient() {
    }

    public AdminApiClient(String addressUrl, String accessToken) {
        this.addressUrl = addressUrl;
        this.accessToken = accessToken;

        // valid
        if (!this.addressUrl.endsWith("/")) {
            this.addressUrl = this.addressUrl + "/";
        }
    }

    @Override
    public Result<String> callback(List<CallbackModel> callbackParamList) {
        return JobRemotingUtil.postBody(addressUrl + "api/callback", accessToken, timeout, callbackParamList, String.class);
    }

    @Override
    public Result<String> registry(RegistryModel registryModel) {
        return JobRemotingUtil.postBody(addressUrl + "api/registry", accessToken, timeout, registryModel, String.class);
    }

    @Override
    public Result<String> registryRemove(RegistryModel registryModel) {
        return JobRemotingUtil.postBody(addressUrl + "api/registryRemove", accessToken, timeout, registryModel, String.class);
    }

}
