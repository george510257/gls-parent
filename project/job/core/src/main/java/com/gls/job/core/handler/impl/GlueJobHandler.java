package com.gls.job.core.handler.impl;

import com.gls.job.core.context.XxlJobHelper;
import com.gls.job.core.handler.IJobHandler;

/**
 * glue job handler
 *
 * @author george 2016-5-19 21:05:45
 */
public class GlueJobHandler extends IJobHandler {

    private long glueUpdateTime;
    private IJobHandler jobHandler;

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
