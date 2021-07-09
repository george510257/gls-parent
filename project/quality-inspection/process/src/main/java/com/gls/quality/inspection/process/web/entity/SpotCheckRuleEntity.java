package com.gls.quality.inspection.process.web.entity;

import com.gls.starter.data.jpa.annotations.Comment;
import com.gls.starter.data.jpa.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.Entity;

/**
 * @author george
 */
@Entity
@Data
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Comment("抽检规则表")
public class SpotCheckRuleEntity extends BaseEntity {
    @Comment("抽检计划id")
    private Long spotCheckId;
    @Comment("抽取数量")
    private Integer spotCheckNumber;
    @Comment("抽取规则类型 1:随机，2:得分最低，3:违规项最多，4.指定违规项")
    private Integer ruleType;
    @Comment("评分项id")
    private Long scoreItemId;
    @Comment("评分项类型 1:评分项，2:组合评分项")
    private Integer scoreItemType;
}
