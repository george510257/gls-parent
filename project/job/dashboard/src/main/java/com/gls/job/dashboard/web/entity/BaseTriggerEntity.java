package com.gls.job.dashboard.web.entity;

import com.gls.job.dashboard.web.entity.enums.MisfireInstruction;
import com.gls.starter.data.jpa.annotations.Comment;
import com.gls.starter.data.jpa.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;
import java.util.Date;

/**
 * @author george
 */
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Data
@MappedSuperclass
public abstract class BaseTriggerEntity extends BaseEntity {
    @Comment("触发器组")
    private String groupName = "DEFAULT";
    @Comment("描述")
    private String description;
    @Comment("开始时间")
    private Date startTime = new Date();
    @Comment("结束时间")
    private Date endTime;
    @Comment("")
    private Integer priority = 5;
    @Comment("")
    private String calendarName;
    @OneToOne
    private JobDetailEntity jobDetail;
    @Comment("失败说明")
    @Enumerated(EnumType.STRING)
    private MisfireInstruction misfireInstruction;
}
