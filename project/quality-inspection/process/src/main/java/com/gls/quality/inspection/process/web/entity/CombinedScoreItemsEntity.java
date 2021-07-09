package com.gls.quality.inspection.process.web.entity;

import com.gls.starter.data.jpa.annotations.Comment;
import com.gls.starter.data.jpa.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * @author george
 */
@Entity
@Data
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Comment("评分-评分模板-组合评分项")
public class CombinedScoreItemsEntity extends BaseEntity {
    @Column
    @Comment("评分模版ID")
    private Integer scoreTemplateId;
    @Column
    @Comment("评分项id，逗号分隔")
    private String scoreItemsIds;
    @Column(length = 500)
    @Comment("显示名称")
    private String displayName;
    @Column(length = 1000)
    @Comment("组合评分项名称")
    private String scoreItemsNames;
    @Column(length = 32)
    @Comment("评分策略 NONE表示全部不出现，ALL表示全部出现，ORDER表示顺序出现，Q1代表出现其一，Q2代表出现其二 以此类推，DQ1代表不出现其一，DQ2代表不出现其二以此类推")
    private String scoreStrategy;
    @Column
    @Comment("质检对象 1表示客服，2表示顾客")
    private Integer inspectionObject;
    @Column
    @Comment("打分属性 1表示加分项，0表示减分项，2中性项")
    private Integer scoreAttribute;
    @Column
    @Comment("分值")
    private Double score;
}
