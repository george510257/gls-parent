package com.gls.quality.inspection.process.web.entity;

import com.gls.starter.data.jpa.annotations.Comment;
import com.gls.starter.data.jpa.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 * @author george
 */
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Comment("客户端信息表")
public class ScoreTemplateEntity extends BaseEntity {
    private Integer templateId;
    private String modelName;
    @ManyToOne
    private ModelEntity model;
    @ManyToOne
    private UserEntity user;
    private String templateName;
    private Integer baseScore;
    private Integer scoreItemsNumber;
    private Boolean status;
    private Boolean scoreBaseline;
}
