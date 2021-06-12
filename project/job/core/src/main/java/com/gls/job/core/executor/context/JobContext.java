package com.gls.job.core.executor.context;

import com.gls.job.core.common.constants.JobConstants;
import lombok.Data;

/**
 * xxl-job context
 *
 * @author xuxueli 2020-05-21
 * [Dear hj]
 */
@Data
public class JobContext {

    /**
     * job id
     */
    private final Long jobId;

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
    private int handleCode = JobConstants.HANDLE_CODE_SUCCESS;
    ;
    /**
     * handleMsg：The simple log msg of job execution
     */
    private String handleMsg = "";

    public JobContext(Long jobId, String jobParam, String jobLogFileName, int shardIndex, int shardTotal) {
        this.jobId = jobId;
        this.jobParam = jobParam;
        this.jobLogFileName = jobLogFileName;
        this.shardIndex = shardIndex;
        this.shardTotal = shardTotal;
    }
}