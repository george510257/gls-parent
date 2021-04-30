package com.gls.job.executor.core.handler.impl;

import com.gls.job.executor.core.handler.JobHandler;
import com.gls.job.executor.core.helper.JobHelper;

import java.util.Date;

/**
 * glue job handler
 *
 * @author george
 */
public class GlueJobHandler implements JobHandler {

    private final JobHandler jobHandler;
    private final Date glueUpdateTime;

    public GlueJobHandler(JobHandler jobHandler, Date glueUpdateTime) {
        this.jobHandler = jobHandler;
        this.glueUpdateTime = glueUpdateTime;
    }

    public Date getGlueUpdateTime() {
        return glueUpdateTime;
    }

    @Override
    public void execute() throws Exception {
        JobHelper.log("----------- glue.version:" + glueUpdateTime + " -----------");
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
