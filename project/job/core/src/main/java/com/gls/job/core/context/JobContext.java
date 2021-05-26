package com.gls.job.core.context;

/**
 * gls-job context
 *
 * @author xuxueli 2020-05-21
 * [Dear hj]
 */
public class JobContext {

    public static final int HANDLE_COCE_SUCCESS = 200;
    public static final int HANDLE_COCE_FAIL = 500;
    public static final int HANDLE_COCE_TIMEOUT = 502;

    // ---------------------- base info ----------------------
    private static InheritableThreadLocal<JobContext> contextHolder = new InheritableThreadLocal<JobContext>(); // support for child thread of job handler)
    /**
     * job id
     */
    private final long jobId;

    // ---------------------- for log ----------------------
    /**
     * job param
     */
    private final String jobParam;

    // ---------------------- for shard ----------------------
    /**
     * job log filename
     */
    private final String jobLogFileName;
    /**
     * shard index
     */
    private final int shardIndex;

    // ---------------------- for handle ----------------------
    /**
     * shard total
     */
    private final int shardTotal;
    /**
     * handleCode：The result status of job execution
     * <p>
     * 200 : success
     * 500 : fail
     * 502 : timeout
     */
    private int handleCode;
    /**
     * handleMsg：The simple log msg of job execution
     */
    private String handleMsg;

    public JobContext(long jobId, String jobParam, String jobLogFileName, int shardIndex, int shardTotal) {
        this.jobId = jobId;
        this.jobParam = jobParam;
        this.jobLogFileName = jobLogFileName;
        this.shardIndex = shardIndex;
        this.shardTotal = shardTotal;

        this.handleCode = HANDLE_COCE_SUCCESS;  // default success
    }

    public static JobContext getJobContext() {
        return contextHolder.get();
    }

    public static void setJobContext(JobContext jobContext) {
        contextHolder.set(jobContext);
    }

    public long getJobId() {
        return jobId;
    }

    public String getJobParam() {
        return jobParam;
    }

    public String getJobLogFileName() {
        return jobLogFileName;
    }

    public int getShardIndex() {
        return shardIndex;
    }

    public int getShardTotal() {
        return shardTotal;
    }

    public int getHandleCode() {
        return handleCode;
    }

    // ---------------------- tool ----------------------

    public void setHandleCode(int handleCode) {
        this.handleCode = handleCode;
    }

    public String getHandleMsg() {
        return handleMsg;
    }

    public void setHandleMsg(String handleMsg) {
        this.handleMsg = handleMsg;
    }

}