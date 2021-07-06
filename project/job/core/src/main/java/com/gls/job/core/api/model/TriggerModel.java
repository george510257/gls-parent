package com.gls.job.core.api.model;

import com.gls.job.core.constants.ExecutorBlockStrategy;
import com.gls.job.core.constants.GlueType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * @author xuxueli
 * @date 16/7/22
 */
@Data
@Accessors(chain = true)
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
