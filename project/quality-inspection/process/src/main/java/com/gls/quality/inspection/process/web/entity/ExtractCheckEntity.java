package com.gls.quality.inspection.process.web.entity;

import com.gls.starter.data.jpa.annotations.Comment;
import com.gls.starter.data.jpa.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.sql.Timestamp;

/**
 * @author george
 */
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Accessors(chain = true)
@Comment("客户端信息表")
public class ExtractCheckEntity extends BaseEntity {
    @ManyToOne
    private UserEntity user;
    private String extractCheckName;
    private Boolean differentiateRole;
    @ManyToOne
    private ScoreTemplateEntity scoreTemplate;
    @ManyToOne
    private IndustryCategoryEntity industryCategory;
    private Integer totalDuration;
    private String modelName;
    private Timestamp deadline;
    private Boolean status;
    private Integer extractCheckSchedule;
    @ManyToOne
    private ModelEntity model;
    private String fileUrl;
    private Boolean resourceType;
    private Boolean isChecked;
    private Boolean isFinished;
    private Integer extractCheckType;
    private Timestamp startTime;
    private Integer baseScore;
    private Timestamp endTime;
}
