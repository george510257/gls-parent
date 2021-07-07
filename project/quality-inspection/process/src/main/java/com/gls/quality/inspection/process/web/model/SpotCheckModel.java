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
public class SpotCheckModel extends BaseModel {
    private String spotCheckName;
    private ExtractCheckModel extractCheck;
    private Date deadline;
    private Integer spotCheckSchedule;
    private Integer dialogueNumber;
    private Boolean isInvalided;
    private Boolean status;
}