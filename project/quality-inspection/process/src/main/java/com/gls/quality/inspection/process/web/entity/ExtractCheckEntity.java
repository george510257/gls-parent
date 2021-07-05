package com.gls.quality.inspection.process.web.entity;

import com.gls.starter.data.jpa.annotations.Comment;
import com.gls.starter.data.jpa.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Entity;
import java.sql.Timestamp;

/**
 * @author george
 */
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Comment("客户端信息表")
public class ExtractCheckEntity extends BaseEntity {
    private String userId;
    private String extractCheckName;
    private Boolean differentiateRole;
    private Integer scoreTemplateId;
    private Integer industryCategoryId;
    private Integer totalDuration;
    private String modelName;
    private Timestamp deadline;
    private Boolean status;
    private Integer extractCheckSchedule;
    private Integer modelId;
    private String fileUrl;
    private Boolean resourceType;
    private Boolean isChecked;
    private Boolean isFinished;
    private Integer extractCheckType;
    private Timestamp startTime;
    private Integer baseScore;
    private Timestamp endTime;
}
