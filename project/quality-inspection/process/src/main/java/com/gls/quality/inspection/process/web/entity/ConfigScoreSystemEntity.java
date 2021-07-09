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
@Comment("系统管理-默认设置-评分体系默认项")
public class ConfigScoreSystemEntity extends BaseEntity {
    @Column
    @Comment("评分策略 1表示出现，0表示不出现")
    private Integer scoreStrategy;
    @Column
    @Comment("打分属性 1表示加分项，0表示减分项，2中性项")
    private Integer scoreAttribute;
    @Column
    @Comment("默认评分项分数")
    private Integer defaultScore;
    @Column
    @Comment("质检得分底线 1表示正常无底线，0表示最低为0分")
    private Integer scoreBaseline;
}
