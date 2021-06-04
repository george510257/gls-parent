package com.xxl.job.admin.core.model;

import com.gls.job.core.api.model.enums.GlueType;
import lombok.Data;

import java.util.Date;

/**
 * xxl-job log for glue, used to track job code process
 *
 * @author xuxueli 2016-5-19 17:57:46
 */
@Data
public class XxlJobLogGlue {

    private Long id;
    private Long jobId;                // 任务主键ID
    private GlueType glueType;        // GLUE类型	#com.gls.job.core.api.model.enums.GlueType
    private String glueSource;
    private String glueRemark;
    private Date addTime;
    private Date updateTime;

}
