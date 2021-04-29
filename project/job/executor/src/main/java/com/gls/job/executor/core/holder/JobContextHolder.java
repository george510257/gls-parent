package com.gls.job.executor.core.holder;

import com.gls.job.core.base.holder.BaseThreadLocalHolder;
import com.gls.job.executor.web.model.JobContextModel;

/**
 * @author george
 */
public class JobContextHolder extends BaseThreadLocalHolder<JobContextModel> {

    private static final JobContextHolder INSTANCE = new JobContextHolder();

    private JobContextHolder() {
    }

    public static JobContextHolder getInstance() {
        return INSTANCE;
    }
}
