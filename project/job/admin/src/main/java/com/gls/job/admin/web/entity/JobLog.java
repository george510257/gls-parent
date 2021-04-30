package com.gls.job.admin.web.entity;

import lombok.Data;

import java.util.Date;

/**
 * gls-job log, used to track trigger process
 *
 * @author george  2015-12-19 23:19:09
 */
@Data
public class JobLog {

    private Long id;

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
