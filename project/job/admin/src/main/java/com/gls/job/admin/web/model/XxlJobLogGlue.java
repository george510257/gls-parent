package com.gls.job.admin.web.model;

import lombok.Data;

import java.util.Date;

/**
 * gls-job log for glue, used to track job code process
 *
 * @author george 2016-5-19 17:57:46
 */
@Data
public class XxlJobLogGlue {

    private int id;
    private int jobId;                // 任务主键ID
    private String glueType;        // GLUE类型	#com.gls.job.core.glue.GlueTypeEnum
    private String glueSource;
    private String glueRemark;
    private Date addTime;
    private Date updateTime;

}
