package com.gls.job.admin.web.model;

import lombok.Data;

import java.util.Date;

@Data
public class XxlJobLogReport {

    private Long id;

    private Date triggerDay;

    private int runningCount;
    private int sucCount;
    private int failCount;

}