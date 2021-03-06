package com.gls.job.executor.core.context;

import com.gls.job.core.constants.JobConstants;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * gls-job context
 *
 * @author xuxueli 2020-05-21
 * [Dear hj]
 */
@Data
@Accessors(chain = true)
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