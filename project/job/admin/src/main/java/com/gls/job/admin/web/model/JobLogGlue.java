package com.gls.job.admin.web.model;

import com.gls.job.core.constants.GlueType;
import lombok.Data;

import java.util.Date;

/**
 * gls-job log for glue, used to track job code process
 *
 * @author xuxueli 2016-5-19 17:57:46
 */
@Data
public class JobLogGlue {
    private Long id;
    /**
     * 任务主键ID
     */
    private Long jobId;
    /**
     * GLUE类型	#com.gls.job.core.constants.GlueType
     */
    private GlueType glueType;
    private String glueSource;
    private String glueRemark;
    private Date addTime;
    private Date updateTime;
}
