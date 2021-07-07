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
public class CombinedScoreItemsModel extends BaseModel {
    private ScoreTemplateModel scoreTemplate;
    private String scoreItemsIds;
    private String displayName;
    private String scoreItemsNames;
    private String scoreStrategy;
    private Boolean inspectionObject;
    private Boolean scoreAttribute;
    private Double score;
}