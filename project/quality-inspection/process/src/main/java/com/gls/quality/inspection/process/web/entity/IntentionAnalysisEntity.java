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
@Comment("")
public class IntentionAnalysisEntity extends BaseEntity {
    @Comment("模型id")
    @ManyToOne
    private ModelEntity model;
    @Column(length = 20)
    @Comment("整通意图")
    private String totalIntention;
    @Column(length = 65535)
    @Comment("通话内容")
    private String content;
    @Column(length = 20)
    @Comment("提取标签")
    private String label;
    @Column(length = 128)
    @Comment("内容详情")
    private String contentDetail;
    @Comment("状态 1:启用 2:关闭")
    private Integer status;
}
