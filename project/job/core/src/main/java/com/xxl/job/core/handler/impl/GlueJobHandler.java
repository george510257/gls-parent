package com.xxl.job.core.handler.impl;

import com.xxl.job.core.context.JobHelper;
import com.xxl.job.core.handler.IJobHandler;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;

/**
 * glue job handler
 *
 * @author xuxueli 2016-5-19 21:05:45
 */
@AllArgsConstructor
public class GlueJobHandler extends IJobHandler {

    private final IJobHandler jobHandler;

    @Getter
    private final Date glueUpdateTime;

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
