package com.gls.job.executor.core.holder;

import com.gls.job.core.base.holder.BaseHolder;
import com.gls.job.executor.core.thread.JobThread;

/**
 * @author george
 */
public class JobThreadHolder extends BaseHolder<Integer, JobThread> {

    private static final JobThreadHolder INSTANCE = new JobThreadHolder();

    private JobThreadHolder() {
    }

    public static JobThreadHolder getInstance() {
        return INSTANCE;
    }

    @Override
    protected void delete(JobThread oldValue, String reason) {
        if (oldValue != null) {
            oldValue.toStop(reason);
        }
    }
}
