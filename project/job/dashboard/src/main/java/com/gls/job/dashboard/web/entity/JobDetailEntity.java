package com.gls.job.dashboard.web.entity;

import com.gls.starter.data.jpa.annotations.Comment;
import com.gls.starter.data.jpa.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Map;

/**
 * @author george
 */
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "JOB_DETAIL")
@Comment("任务信息表")
public class JobDetailEntity extends BaseEntity {
    @Comment("任务组")
    private String groupName = "DEFAULT";
    @Comment("描述")
    private String description;
    @Comment("任务类名")
    private String jobClassName;
    @Comment("任务参数")
    @ElementCollection
    private Map<String, String> jobDataMap;
    @Comment("是否应保持存储状态")
    private Boolean durability = false;
    @Comment("是否支持重试")
    private Boolean shouldRecover = false;
}
