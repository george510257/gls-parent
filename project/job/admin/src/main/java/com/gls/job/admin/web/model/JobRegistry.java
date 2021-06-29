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
public class JobRegistry extends BaseModel {
    private String registryGroup;
    private String registryKey;
    private String registryValue;
    private Date updateTime;
}
