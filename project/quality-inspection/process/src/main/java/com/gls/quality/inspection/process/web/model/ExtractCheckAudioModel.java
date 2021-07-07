package com.gls.quality.inspection.process.web.model;

import com.gls.framework.api.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author george
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class ExtractCheckAudioModel extends BaseModel {
    private String customerServiceId;
    private ExtractCheckModel extractCheck;
    private String dialogueId;
    private Boolean status;
    private String audioUrl;
    private Long duration;
    private Integer dialogueNumber;
    private Boolean resourceType;
    private Boolean isTranslated;
    private Boolean isLooked;
    private Integer serviceCheckRate;
    private Integer userCheckRate;
    private Integer violationCount;
    private Double recheckScore;
    private Double checkScore;
    private Double spotCheckScore;
    private Boolean isChecked;
    private Boolean isSpotChecked;
    private Date checkTime;
    private String customerMobile;
    private Boolean isInvalid;
    private Integer recheckUserId;
    private String applyCode;
    private Integer distributeUserId;
}