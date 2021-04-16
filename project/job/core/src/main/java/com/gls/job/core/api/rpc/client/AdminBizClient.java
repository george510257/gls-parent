package com.gls.job.core.api.rpc.client;

import com.gls.job.core.api.model.CallbackModel;
import com.gls.job.core.api.model.RegistryModel;
import com.gls.job.core.api.model.Result;
import com.gls.job.core.api.rpc.AdminBiz;
import com.gls.job.core.util.XxlJobRemotingUtil;

import java.util.List;

/**
 * admin api test
 *
 * @author george 2017-07-28 22:14:52
 */
public class AdminBizClient implements AdminBiz {

    private String addressUrl;
    private String accessToken;
    private int timeout = 3;

    public AdminBizClient() {
    }

    public AdminBizClient(String addressUrl, String accessToken) {
        this.addressUrl = addressUrl;
        this.accessToken = accessToken;

        // valid
        if (!this.addressUrl.endsWith("/")) {
            this.addressUrl = this.addressUrl + "/";
        }
    }

    @Override
    public Result<String> callback(List<CallbackModel> callbackModelList) {
        return XxlJobRemotingUtil.postBody(addressUrl + "api/callback", accessToken, timeout, callbackModelList, String.class);
    }

    @Override
    public Result<String> registry(RegistryModel registryParam) {
        return XxlJobRemotingUtil.postBody(addressUrl + "api/registry", accessToken, timeout, registryParam, String.class);
    }

    @Override
    public Result<String> registryRemove(RegistryModel registryParam) {
        return XxlJobRemotingUtil.postBody(addressUrl + "api/registryRemove", accessToken, timeout, registryParam, String.class);
    }

}
