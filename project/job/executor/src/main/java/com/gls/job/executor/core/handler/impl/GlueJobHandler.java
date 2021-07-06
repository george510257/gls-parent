package com.gls.job.executor.core.handler.impl;

import com.gls.job.executor.core.handler.JobHandler;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

/**
 * glue job handler
 *
 * @author xuxueli 2016-5-19 21:05:45
 */
@Slf4j
@Data
@Accessors(chain = true)
@AllArgsConstructor
public class GlueJobHandler implements JobHandler {
    private final JobHandler jobHandler;
    private final Date glueUpdateTime;

    @Override
    public void destroy() throws Exception {
        this.jobHandler.destroy();
    }

    @Override
    public void execute() throws Exception {
        log.info("----------- glue.version:" + glueUpdateTime + " -----------");
        jobHandler.execute();
    }

    @Override
    public void init() throws Exception {
        this.jobHandler.init();
    }
}
