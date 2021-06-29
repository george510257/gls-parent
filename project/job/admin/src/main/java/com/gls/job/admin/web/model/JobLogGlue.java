package com.gls.job.admin.web.model;

import com.gls.framework.api.model.BaseModel;
import com.gls.job.core.constants.GlueType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * @author george
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class JobLogGlue extends BaseModel {
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
