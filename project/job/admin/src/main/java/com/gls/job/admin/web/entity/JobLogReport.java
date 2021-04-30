package com.gls.job.admin.web.entity;

import lombok.Data;

import java.util.Date;

@Data
public class JobLogReport {

    private Long id;

    private Date triggerDay;

    private int runningCount;
    private int sucCount;
    private int failCount;

}
