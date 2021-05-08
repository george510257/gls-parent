package com.gls.job.dashboard.web.entity;

import com.gls.starter.data.jpa.annotations.Comment;
import com.gls.starter.data.jpa.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Entity;

/**
 * @author george
 */
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Comment("任务信息表")
public class JobDetailEntity extends BaseEntity {

    @Comment("任务组")
    private String group;

    @Comment("描述")
    private String description;

    @Comment("任务类名")
    private String className;

    @Comment("是否应保持存储状态")
    private Boolean durable;

    @Comment("是否非并发")
    private Boolean nonConcurrent;

    @Comment("是否支持更新初始参数")
    private Boolean updateData;

    @Comment("是否支持重试")
    private Boolean requestsRecovery;
}
