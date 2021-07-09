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
@Comment("")
public class LabelEntity extends BaseEntity {
    @Column
    @Comment("模型id")
    private Integer modelId;
    @Column(length = 200)
    @Comment("语义类别")
    private String semanticCategory;
    @Column(length = 200)
    @Comment("语义标签")
    private String semanticLabel;
    @Column
    @Comment("语义角色 1:客服 2:客户")
    private Integer semanticRole;
    @Column
    @Comment("状态 1:启用 2:禁用")
    private Integer status;
    @Column(length = 16777215)
    @Comment("扩展问")
    private String extQuestion;
    @Column(length = 16777215)
    @Comment("推荐扩展问")
    private String recommendExtQuestion;
    @Column(length = 16777215)
    @Comment("规则")
    private String rule;
}
