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
@Comment("抽检分配表")
public class SpotCheckDistributeEntity extends BaseEntity {
    @Column
    @Comment("抽检计划id")
    private Integer spotCheckId;
    @Column
    @Comment("质检员id")
    private Integer userId;
    @Column
    @Comment("分配比例")
    private Integer distributeRate;
    @Column
    @Comment("是否分配 0:待分配，1:已分配，2:已暂停")
    private Integer status;
    @Column
    @Comment("是否删除 0:未删除，1:已删除")
    private Integer isDeleted;
}
