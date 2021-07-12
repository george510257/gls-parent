package com.gls.quality.inspection.process.web.entity;

import com.gls.starter.data.jpa.annotations.Comment;
import com.gls.starter.data.jpa.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.Column;
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
@Comment("特殊标签表(整通，复合)")
public class SpecialLabelEntity extends BaseEntity {
    @Comment("模型id")
    @ManyToOne
    private ModelEntity model;
    @Comment("标签类型 3:整通标签 4:复合标签")
    private Integer type;
    @Column(length = 1024)
    @Comment("通话id数组 例:[2333,2334,2335]")
    private String callIds;
    @Comment("状态 1:启用 2:禁用")
    private Integer status;
    @Column(length = 1024)
    @Comment("子标签数组 例子:[\"标签1\", \"标签2\"]")
    private String childLabel;
    @Column(length = 512)
    @Comment("重点信息")
    private String digest;
}
