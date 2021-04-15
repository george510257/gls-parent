package com.gls.job.core.biz.client;

import com.gls.job.core.biz.ExecutorBiz;
import com.gls.job.core.biz.model.*;
import com.gls.job.core.util.XxlJobRemotingUtil;

/**
 * admin api test
 *
 * @author xuxueli 2017-07-28 22:14:52
 */
public class ExecutorBizClient implements ExecutorBiz {

    private String addressUrl;
    private String accessToken;
    private int timeout = 3;

    public ExecutorBizClient() {
    }

    public ExecutorBizClient(String addressUrl, String accessToken) {
        this.addressUrl = addressUrl;
        this.accessToken = accessToken;

        // valid
        if (!this.addressUrl.endsWith("/")) {
            this.addressUrl = this.addressUrl + "/";
        }
    }

    @Override
    public ReturnT<String> beat() {
        return XxlJobRemotingUtil.postBody(addressUrl + "beat", accessToken, timeout, "", String.class);
    }

    @Override
    public ReturnT<String> idleBeat(IdleBeatParam idleBeatParam) {
        return XxlJobRemotingUtil.postBody(addressUrl + "idleBeat", accessToken, timeout, idleBeatParam, String.class);
    }

    @Override
    public ReturnT<String> run(TriggerParam triggerParam) {
        return XxlJobRemotingUtil.postBody(addressUrl + "run", accessToken, timeout, triggerParam, String.class);
    }

    @Override
    public ReturnT<String> kill(KillParam killParam) {
        return XxlJobRemotingUtil.postBody(addressUrl + "kill", accessToken, timeout, killParam, String.class);
    }

    @Override
    public ReturnT<LogResult> log(LogParam logParam) {
        return XxlJobRemotingUtil.postBody(addressUrl + "log", accessToken, timeout, logParam, LogResult.class);
    }

}