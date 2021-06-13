package com.gls.job.executor.core.thread;

import com.gls.job.core.base.BaseHolder;
import com.gls.job.executor.core.handler.JobHandler;
import com.gls.job.executor.core.queue.CallbackQueueHolder;
import com.gls.job.executor.web.service.JobLogService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author george
 */
@Component
public class JobThreadHolder extends BaseHolder<Long, JobThread> {

    @Resource
    public CallbackQueueHolder callbackQueueHolder;
    @Resource
    private JobLogService jobLogService;

    public JobThread registByJobHandler(Long key, JobHandler handler, String reason) {
        JobThread value = new JobThread(key, handler, jobLogService, this, callbackQueueHolder);
        value.start();
        regist(key, value, reason);
        return value;
    }

    @Override
    protected void delete(JobThread oldValue, String reason) {
        oldValue.toStop(reason);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
    }
}
