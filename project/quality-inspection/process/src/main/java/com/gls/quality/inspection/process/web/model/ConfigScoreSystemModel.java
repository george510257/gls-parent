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
public class ConfigScoreSystemModel extends BaseModel {
    private Boolean scoreStrategy;
    private Boolean scoreAttribute;
    private Integer defaultScore;
    private Boolean scoreBaseline;
}