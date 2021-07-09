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
@Comment("公司(租户)配置表")
public class CompanyConfigEntity extends BaseEntity {
    @Column(length = 1000)
    @Comment("公司logo")
    private String imgLogo;
    @Column(length = 1000)
    @Comment("公司封面")
    private String imgCover;
    @Column
    @Comment("转写并发数")
    private Integer translateConcurrent;
    @Column
    @Comment("模型数量")
    private Integer modelNum;
    @Column
    @Comment("管理员数量")
    private Integer adminNum;
    @Column
    @Comment("客服数量")
    private Integer customerServiceNum;
    @Column
    @Comment("质检员数量")
    private Integer inspectorNum;
}
