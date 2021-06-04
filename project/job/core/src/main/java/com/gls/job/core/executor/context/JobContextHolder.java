package com.gls.job.core.executor.context;

/**
 * @author george
 */
public class JobContextHolder {

    private static final InheritableThreadLocal<JobContext> JOB_CONTEXT_INHERITABLE_THREAD_LOCAL = new InheritableThreadLocal<>();

    public static JobContext getJobContext() {
        return JOB_CONTEXT_INHERITABLE_THREAD_LOCAL.get();
    }

    public static void setJobContext(JobContext jobContext) {
        JOB_CONTEXT_INHERITABLE_THREAD_LOCAL.set(jobContext);
    }
}
