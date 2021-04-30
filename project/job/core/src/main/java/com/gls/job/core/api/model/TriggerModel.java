package com.gls.job.core.api.model;

import com.gls.job.core.api.model.enums.ExecutorBlockStrategy;
import com.gls.job.core.api.model.enums.GlueType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author george
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TriggerModel implements Serializable {

    private Long jobId;

    private String executorHandler;
    private String executorParams;
    private ExecutorBlockStrategy executorBlockStrategy;
    private Integer executorTimeout;

    private Long logId;
    private Date logDateTime;

    private GlueType glueType;
    private String glueSource;
    private Date glueUpdateTime;

    private Integer broadcastIndex;
    private Integer broadcastTotal;

}
