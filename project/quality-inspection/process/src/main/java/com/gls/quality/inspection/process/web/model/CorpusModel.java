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
public class CorpusModel extends BaseModel {
    private ModelModel model;
    private String content;
    private String audioUrl;
    private Boolean translateStatus;
    private Integer status;
    private Integer isAnnotated;
    private Integer audioLen;
    private Integer totalLabelId;
    private Integer complexLabelId;
    private String semanticLabelList;
}