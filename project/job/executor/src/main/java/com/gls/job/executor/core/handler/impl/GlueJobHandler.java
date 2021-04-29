package com.gls.job.executor.core.handler.impl;

import com.gls.job.executor.core.handler.IJobHandler;
import com.gls.job.executor.core.helper.XxlJobHelper;

/**
 * glue job handler
 *
 * @author george
 */
public class GlueJobHandler implements IJobHandler {

    private final IJobHandler jobHandler;
    private final long glueUpdateTime;

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
