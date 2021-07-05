package com.gls.quality.inspection.process.web.entity;

import com.gls.starter.data.jpa.annotations.Comment;
import com.gls.starter.data.jpa.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Entity;

/**
 * @author george
 */
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Comment("客户端信息表")
public class ScoreItemsEntity extends BaseEntity {
    private Integer scoreTemplateId;
    private Boolean type;
    private String scoreItemsTitle;
    private Boolean scoreStrategy;
    private Boolean inspectionObject;
    private Boolean scoreAttribute;
    private Double score;
    private Boolean scorePrinciple;
}
