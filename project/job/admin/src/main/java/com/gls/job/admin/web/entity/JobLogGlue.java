package com.gls.job.admin.web.entity;

import com.gls.job.core.api.model.enums.GlueType;
import lombok.Data;

import java.util.Date;

/**
 * gls-job log for glue, used to track job code process
 *
 * @author george 2016-5-19 17:57:46
 */
@Data
public class JobLogGlue {

    private Long id;
    private Long jobId;                // 任务主键ID
    private GlueType glueType;        // GLUE类型	#com.gls.job.core.glue.GlueTypeEnum
    private String glueSource;
    private String glueRemark;
    private Date addTime;
    private Date updateTime;

}
