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
public class ScoreItemsModel extends BaseModel {
    private ScoreTemplateModel scoreTemplate;
    private Boolean type;
    private String scoreItemsTitle;
    private Boolean scoreStrategy;
    private Boolean inspectionObject;
    private Boolean scoreAttribute;
    private Double score;
    private Boolean scorePrinciple;
}