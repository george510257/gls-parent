package com.gls.job.core.executor.thread;

import com.gls.job.core.common.base.BaseHolder;
import com.gls.job.core.executor.handler.JobHandler;
import com.gls.job.core.executor.web.service.JobLogService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author george
 */
@Component
public class JobThreadHolder extends BaseHolder<Long, JobThread> {

    @Resource
    private JobLogService jobLogService;

    public JobThread registByJobHandler(Long key, JobHandler handler, String reason) {
        JobThread value = new JobThread(key, handler, jobLogService, this);
        value.start();
        super.regist(key, value, reason);
        return value;
    }

    @Override
    protected void delete(JobThread oldValue, String reason) {
        oldValue.toStop(reason);
        super.delete(oldValue, reason);
    }
}