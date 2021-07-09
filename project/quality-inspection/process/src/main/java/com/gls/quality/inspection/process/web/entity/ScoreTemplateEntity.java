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
@Comment("评分-评分模板")
public class ScoreTemplateEntity extends BaseEntity {
    @Column
    @Comment("评分模板id 6位自增 000001开始")
    private Integer templateId;
    @Column
    @Comment("模版名称")
    private String modelName;
    @Column
    @Comment("模型id ")
    private Integer modelId;
    @Column
    @Comment("系统用户id")
    private Integer userId;
    @Column(length = 50)
    @Comment("评分模板名称")
    private String templateName;
    @Column
    @Comment("基础分数")
    private Integer baseScore;
    @Column
    @Comment("评分项数量")
    private Integer scoreItemsNumber;
    @Column
    @Comment("状态0:未开启 1:开启")
    private Integer status;
    @Column
    @Comment("质检得分底线 0表示最低为0分，1表示正常无底线")
    private Integer scoreBaseline;
}
