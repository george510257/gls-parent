package com.gls.job.core.api.rpc.client;

import com.gls.framework.api.result.Result;
import com.gls.job.core.api.model.*;
import com.gls.job.core.api.rpc.ExecutorApi;
import com.gls.job.core.util.JobRemotingUtil;

/**
 * admin api test
 *
 * @author xuxueli 2017-07-28 22:14:52
 */
public class ExecutorApiClient implements ExecutorApi {

    private final int timeout = 3;
    private String addressUrl;
    private String accessToken;

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
        return JobRemotingUtil.postBody(addressUrl + "beat", accessToken, timeout, "", String.class);
    }

    @Override
    public Result<String> idleBeat(IdleBeatModel idleBeatModel) {
        return JobRemotingUtil.postBody(addressUrl + "idleBeat", accessToken, timeout, idleBeatModel, String.class);
    }

    @Override
    public Result<String> run(TriggerModel triggerModel) {
        return JobRemotingUtil.postBody(addressUrl + "run", accessToken, timeout, triggerModel, String.class);
    }

    @Override
    public Result<String> kill(KillModel killModel) {
        return JobRemotingUtil.postBody(addressUrl + "kill", accessToken, timeout, killModel, String.class);
    }

    @Override
    public Result<LogResultModel> log(LogModel logModel) {
        return JobRemotingUtil.postBody(addressUrl + "log", accessToken, timeout, logModel, LogResultModel.class);
    }

}
