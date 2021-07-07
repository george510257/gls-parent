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
public class ExtractCheckResultModel extends BaseModel {
    private ExtractCheckAudioModel extractCheckAudio;
    private String violationsItem;
    private Double violationsScore;
    private String category;
    private String paragraph;
    private ScoreItemsModel scoreItems;
    private Integer scoreItemType;
    private Boolean scoreAttribute;
    private Boolean inspectionObject;
    private String keyInfo;
    private Boolean type;
    private Boolean recheckStatus;
    private String representation;
    private Boolean isMissed;
    private Boolean isError;
    private String scoreStrategy;
}