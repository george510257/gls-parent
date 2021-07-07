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
public class IndustryBuzzwordsModel extends BaseModel {
    private IndustryCategoryModel industryCategory;
    private String industryCategoryIds;
    private String buzzwordsText;
    private Integer buzzwordsNumber;
}