package com.gls.job.admin.web.model;

import lombok.Data;

import java.util.Date;

/**
 * @author george
 */
@Data
public class JobLogReport {

    private Long id;

    private Date triggerDay;

    private Long runningCount;
    private Long sucCount;
    private Long failCount;

}
