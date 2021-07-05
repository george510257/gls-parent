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
public class CombinedScoreItemsEntity extends BaseEntity {
    @ManyToOne
    private ScoreTemplateEntity scoreTemplate;
    private String scoreItemsIds;
    private String displayName;
    private String scoreItemsNames;
    private String scoreStrategy;
    private Boolean inspectionObject;
    private Boolean scoreAttribute;
    private Double score;
}
