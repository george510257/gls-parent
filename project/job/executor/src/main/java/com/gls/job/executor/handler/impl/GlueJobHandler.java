package com.gls.job.executor.handler.impl;

import com.gls.job.executor.handler.IJobHandler;
import com.gls.job.executor.helper.XxlJobHelper;

/**
 * glue job handler
 *
 * @author george 2016-5-19 21:05:45
 */
public class GlueJobHandler extends IJobHandler {

    private final long glueUpdateTime;
    private final IJobHandler jobHandler;

    public GlueJobHandler(IJobHandler jobHandler, long glueUpdateTime) {
        this.jobHandler = jobHandler;
        this.glueUpdateTime = glueUpdateTime;
    }

    public long getGlueUpdateTime() {
        return glueUpdateTime;
    }

    @Override
    public void execute() throws Exception {
        XxlJobHelper.log("----------- glue.version:" + glueUpdateTime + " -----------");
        jobHandler.execute();
    }

    @Override
    public void init() throws Exception {
        this.jobHandler.init();
    }

    @Override
    public void destroy() throws Exception {
        this.jobHandler.destroy();
    }
}
