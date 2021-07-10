package com.gls.quality.inspection.process.web.entity;

import com.gls.starter.data.jpa.annotations.Comment;
import com.gls.starter.data.jpa.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

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
@Comment("抽检分配表")
public class SpotCheckDistributeEntity extends BaseEntity {
    @Comment("抽检计划id")
    @ManyToOne
    private SpotCheckEntity spotCheck;
    @Comment("质检员id")
    @ManyToOne
    private UserEntity user;
    @Comment("分配比例")
    private Integer distributeRate;
    @Comment("是否分配 0:待分配，1:已分配，2:已暂停")
    private Integer status;
}
