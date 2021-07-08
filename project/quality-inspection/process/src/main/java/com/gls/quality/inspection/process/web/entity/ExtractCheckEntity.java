package com.gls.quality.inspection.process.web.entity;

import com.gls.starter.data.jpa.annotations.Comment;
import com.gls.starter.data.jpa.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import java.sql.Timestamp;

/**
 * @author george
 */
@Entity
@Data
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Comment("客户端信息表")
public class ExtractCheckEntity extends BaseEntity {
    private Integer id;
    private String userId;
    private String extractCheckName;
    private Byte differentiateRole;
    private Integer scoreTemplateId;
    private Integer industryCategoryId;
    private Integer totalDuration;
    private String modelName;
    private Timestamp deadline;
    private Byte status;
    private Integer extractCheckSchedule;
    private Integer modelId;
    private String fileUrl;
    private Byte resourceType;
    private Byte isChecked;
    private Byte isFinished;
    private Timestamp createTime;
    private Timestamp updateTime;
    private Byte isDeleted;
    private Integer extractCheckType;
    private Timestamp startTime;
    private Integer baseScore;
    private Timestamp endTime;
    private Long companyId;
}