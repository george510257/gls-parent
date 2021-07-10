package com.gls.quality.inspection.process.web.entity;

import com.gls.starter.data.jpa.annotations.Comment;
import com.gls.starter.data.jpa.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 * @author george
 */
@Entity
@Data
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Comment("评分-评分模板-评分项")
public class ScoreItemsEntity extends BaseEntity {
    @Comment("模板ID")
    @ManyToOne
    private ScoreTemplateEntity scoreTemplate;
    @Comment("类型  1.语义标签 2.语音标签 3.整通标签 4.复合标签")
    private Integer type;
    @Comment("评分项")
    private String scoreItemsTitle;
    @Comment("评分策略 1表示出现，0表示不出现")
    private Integer scoreStrategy;
    @Comment("质检对象 1表示客服，2表示顾客  3.整通")
    private Integer inspectionObject;
    @Comment("打分属性 1表示加分项，0表示减分项，2中性项")
    private Integer scoreAttribute;
    @Comment("分值")
    private Double score;
    @Comment("得分原则：1累计，2仅一次")
    private Integer scorePrinciple;
}
