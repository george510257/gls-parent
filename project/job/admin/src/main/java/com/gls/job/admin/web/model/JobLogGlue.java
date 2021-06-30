package com.gls.job.admin.web.model;

import com.gls.framework.api.model.BaseModel;
import com.gls.job.core.constants.GlueType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

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
    @NotBlank(message = "请输入源码备注")
    @Length(min = 4, max = 100, message = "源码备注长度限制为4~100")
    private String glueRemark;
}
