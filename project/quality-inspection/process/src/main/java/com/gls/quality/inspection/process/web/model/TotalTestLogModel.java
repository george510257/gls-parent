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
public class TotalTestLogModel extends BaseModel {
    private TotalTestTaskModel totalTestTask;
    private String testContent;
    private String expectLabel;
    private Boolean expectRole;
    private String recLabel;
    private String recRule;
    private Boolean recRole;
    private String module;
    private Double score;
    private Integer pass;
}