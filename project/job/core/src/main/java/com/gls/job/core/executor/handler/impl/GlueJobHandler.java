package com.gls.job.core.executor.handler.impl;

import com.gls.job.core.executor.handler.JobHandler;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

/**
 * glue job handler
 *
 * @author xuxueli 2016-5-19 21:05:45
 */
@Slf4j
@Data
@AllArgsConstructor
public class GlueJobHandler implements JobHandler {

    private final JobHandler jobHandler;
    private final Date glueUpdateTime;

    @Override
    public void execute() throws Exception {
        log.info("----------- glue.version:" + glueUpdateTime + " -----------");
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