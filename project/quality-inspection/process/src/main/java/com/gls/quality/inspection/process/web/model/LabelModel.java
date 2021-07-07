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
public class LabelModel extends BaseModel {
    private ModelModel model;
    private String semanticCategory;
    private String semanticLabel;
    private Integer semanticRole;
    private Integer status;
    private String extQuestion;
    private String recommendExtQuestion;
    private String rule;
}