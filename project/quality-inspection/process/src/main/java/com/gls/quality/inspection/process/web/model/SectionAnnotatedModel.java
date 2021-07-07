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
public class SectionAnnotatedModel extends BaseModel {
    private CorpusModel corpus;
    private String selection;
    private String totalLabel;
    private String singleLabel;
    private String complexLabel;
    private String extqRule;
}