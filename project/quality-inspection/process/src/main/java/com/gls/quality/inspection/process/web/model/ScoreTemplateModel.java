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
public class ScoreTemplateModel extends BaseModel {
    private Integer templateId;
    private String modelName;
    private ModelModel model;
    private UserModel user;
    private String templateName;
    private Integer baseScore;
    private Integer scoreItemsNumber;
    private Boolean status;
    private Boolean scoreBaseline;
}