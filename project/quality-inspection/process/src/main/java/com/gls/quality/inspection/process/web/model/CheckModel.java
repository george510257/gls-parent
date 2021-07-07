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
public class CheckModel extends BaseModel {
    private String applyCode;
    private String node;
    private String customerName;
    private String idCard;
    private String productName;
    private String companyName;
    private String checkType;
    private Boolean status;
}