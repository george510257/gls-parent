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
public class CompanyConfigModel extends BaseModel {
    private String imgLogo;
    private String imgCover;
    private Boolean translateConcurrent;
    private Boolean modelNum;
    private Boolean adminNum;
    private Boolean customerServiceNum;
    private Boolean inspectorNum;
}