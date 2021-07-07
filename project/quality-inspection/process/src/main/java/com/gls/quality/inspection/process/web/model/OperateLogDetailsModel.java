package com.gls.quality.inspection.process.web.model;

import com.gls.framework.api.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author george
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class OperateLogDetailsModel extends BaseModel {
    private String module;
    private String operateName;
    private String logDetails;
    private String createBy;
    private String username;
    private String realname;
}