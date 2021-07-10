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
@Comment("业务线表")
public class BusinessLineEntity extends BaseEntity {
    @Column(length = 32)
    @Comment("产品大类：A:信贷，B:车贷，C:房贷，AB:信贷车贷")
    private String belType;
    @Comment("质检类型：1电核，2面签")
    private Integer checkType;
    @Column(length = 32)
    @Comment("贷款用途（信车房），生产经营，生活消费")
    private String purpose;
    @Column(length = 32)
    @Comment("缴费方式：期缴，趸交（信车房）")
    private String payType;
    @Comment("模型id")
    @ManyToOne
    private ModelEntity model;
    @Comment("拉取数量上限")
    private Integer count;
    @Comment("备注")
    private String remark;
}
