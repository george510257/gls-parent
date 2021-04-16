package com.gls.job.core.api.model;

import java.io.Serializable;

/**
 * @author xuxueli 2020-04-11 22:27
 */
public class IdleBeatModel implements Serializable {
    private static final long serialVersionUID = 42L;
    private int jobId;

    public IdleBeatModel() {
    }

    public IdleBeatModel(int jobId) {
        this.jobId = jobId;
    }

    public int getJobId() {
        return jobId;
    }

    public void setJobId(int jobId) {
        this.jobId = jobId;
    }

}