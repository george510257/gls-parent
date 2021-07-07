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
public class ExtractCheckModel extends BaseModel {
    private UserModel user;
    private String extractCheckName;
    private Boolean differentiateRole;
    private ScoreTemplateModel scoreTemplate;
    private IndustryCategoryModel industryCategory;
    private Integer totalDuration;
    private String modelName;
    private Date deadline;
    private Boolean status;
    private Integer extractCheckSchedule;
    private ModelModel model;
    private String fileUrl;
    private Boolean resourceType;
    private Boolean isChecked;
    private Boolean isFinished;
    private Integer extractCheckType;
    private Date startTime;
    private Integer baseScore;
    private Date endTime;
}