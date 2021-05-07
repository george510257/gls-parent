package com.gls.job.dashboard.core.job;

import lombok.extern.slf4j.Slf4j;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

/**
 * @author george
 */
@Slf4j
@Component
public class BaseJob extends QuartzJobBean {
    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        log.info("hello,job!! :{}", context.getJobDetail().getKey());
        // 获取参数
        JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
        // 业务逻辑 ...
        log.info("------spring boot quart zone job执行" + jobDataMap.get("name").toString() + "###############" + context.getTrigger());
    }
}
