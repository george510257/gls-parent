package com.gls.job.core.api.rpc.client;

import com.gls.job.core.api.model.*;
import com.gls.job.core.api.rpc.ExecutorApi;
import com.xxl.job.core.util.XxlJobRemotingUtil;

/**
 * admin api test
 *
 * @author xuxueli 2017-07-28 22:14:52
 */
public class ExecutorApiClient implements ExecutorApi {

    private String addressUrl;
    private String accessToken;
    private int timeout = 3;

    public ExecutorApiClient() {
    }

    public ExecutorApiClient(String addressUrl, String accessToken) {
        this.addressUrl = addressUrl;
        this.accessToken = accessToken;

        // valid
        if (!this.addressUrl.endsWith("/")) {
            this.addressUrl = this.addressUrl + "/";
        }
    }

    @Override
    public Result<String> beat() {
        return XxlJobRemotingUtil.postBody(addressUrl + "beat", accessToken, timeout, "", String.class);
    }

    @Override
    public Result<String> idleBeat(IdleBeatModel idleBeatModel) {
        return XxlJobRemotingUtil.postBody(addressUrl + "idleBeat", accessToken, timeout, idleBeatModel, String.class);
    }

    @Override
    public Result<String> run(TriggerModel triggerModel) {
        return XxlJobRemotingUtil.postBody(addressUrl + "run", accessToken, timeout, triggerModel, String.class);
    }

    @Override
    public Result<String> kill(KillModel killModel) {
        return XxlJobRemotingUtil.postBody(addressUrl + "kill", accessToken, timeout, killModel, String.class);
    }

    @Override
    public Result<LogResultModel> log(LogModel logModel) {
        return XxlJobRemotingUtil.postBody(addressUrl + "log", accessToken, timeout, logModel, LogResultModel.class);
    }

}
