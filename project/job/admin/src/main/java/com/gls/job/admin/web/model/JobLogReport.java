package com.gls.job.admin.web.model;

import com.gls.framework.api.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * @author george
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class JobLogReport extends BaseModel {
    private Date triggerDay;
    private Long runningCount;
    private Long sucCount;
    private Long failCount;
}
