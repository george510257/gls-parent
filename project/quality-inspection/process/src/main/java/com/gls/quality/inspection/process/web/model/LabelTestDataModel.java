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
public class LabelTestDataModel extends BaseModel {
    private ModelModel model;
    private Integer status;
    private Double totalLabelPassingRate;
    private Double voiceCheckPassingRate;
    private Double semanticLabelPassingRate;
    private Double complexLabelPassingRate;
}