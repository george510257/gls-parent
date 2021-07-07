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
public class TotalTestTaskModel extends BaseModel {
    private UserModel user;
    private ModelModel model;
    private String testName;
    private Boolean testType;
    private Integer status;
    private Double passRate;
}