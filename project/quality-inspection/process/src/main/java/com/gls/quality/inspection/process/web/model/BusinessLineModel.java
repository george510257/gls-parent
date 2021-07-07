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
public class BusinessLineModel extends BaseModel {
    private String belType;
    private Integer checkType;
    private String purpose;
    private String payType;
    private ModelModel model;
    private Integer count;
    private String remark;
}