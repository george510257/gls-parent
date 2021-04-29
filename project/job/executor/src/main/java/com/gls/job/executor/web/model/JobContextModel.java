package com.gls.job.executor.web.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author georg
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobContextModel {

    /**
     * job id
     */
    private long jobId;
    /**
     * job param
     */
    private String jobParam;
    /**
     * job log filename
     */
    private String jobLogFileName;
    /**
     * shard index
     */
    private int shardIndex;
    /**
     * shard total
     */
    private int shardTotal;
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

    public JobContextModel(long jobId, String jobParam, String jobLogFileName, int shardIndex, int shardTotal) {
        this.jobId = jobId;
        this.jobParam = jobParam;
        this.jobLogFileName = jobLogFileName;
        this.shardIndex = shardIndex;
        this.shardTotal = shardTotal;
    }

}