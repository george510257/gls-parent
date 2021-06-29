package com.gls.job.admin.web.model;

import com.gls.framework.api.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * @author george
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class JobLog extends BaseModel {
    // job info
    private Long jobGroup;
    private Long jobId;
    // execute info
    private String executorAddress;
    private String executorHandler;
    private String executorParam;
    private String executorShardingParam;
    private int executorFailRetryCount;
    // trigger info
    private Date triggerTime;
    private int triggerCode;
    private String triggerMsg;
    // handle info
    private Date handleTime;
    private int handleCode;
    private String handleMsg;
    // alarm info
    private int alarmStatus;
}
