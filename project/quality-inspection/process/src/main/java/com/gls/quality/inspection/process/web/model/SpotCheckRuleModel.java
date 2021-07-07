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
public class SpotCheckRuleModel extends BaseModel {
    private SpotCheckModel spotCheck;
    private Integer spotCheckNumber;
    private Boolean ruleType;
    private ScoreItemsModel scoreItems;
    private Boolean scoreItemType;
}