package com.gls.job.core.api.model;

import com.gls.job.core.api.model.enums.ExecutorBlockStrategy;
import com.gls.job.core.api.model.enums.GlueType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author george
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TriggerModel implements Serializable {

    private int jobId;

    private String executorHandler;
    private String executorParams;
    private ExecutorBlockStrategy executorBlockStrategy;
    private int executorTimeout;

    private long logId;
    private long logDateTime;

    private GlueType glueType;
    private String glueSource;
    private long glueUpdateTime;

    private int broadcastIndex;
    private int broadcastTotal;

}
