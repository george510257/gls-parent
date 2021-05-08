package com.gls.job.dashboard.core.job;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Trigger;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author george
 */
@Slf4j
@Component
public class BaseJob extends QuartzJobBean {
    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        // jobDetail
        JobDetail jobDetail = context.getJobDetail();
        // trigger
        Trigger trigger = context.getTrigger();
        // 获取参数
        Map<String, Object> jobData = context.getJobDetail().getJobDataMap();
        log.info("jobDetail:{}", JSON.toJSONString(jobDetail));
        log.info("trigger:{}", JSON.toJSONString(trigger));
        log.info("jobData:{}", JSON.toJSONString(jobData));
    }
}
