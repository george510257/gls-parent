package com.gls.job.dashboard.web.entity;

import com.gls.job.dashboard.web.entity.enums.MisfireInstruction;
import com.gls.starter.data.jpa.annotations.Comment;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

/**
 * @author george
 */
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Comment("Cron触发器信息表")
public class CronTriggerEntity extends TriggerEntity {

    @Comment("Cron表达式")
    private String cronExpression;

    @Comment("时区")
    private String timeZone;

    @Comment("失败说明")
    @Enumerated(EnumType.STRING)
    private MisfireInstruction misfireInstruction = MisfireInstruction.CRON_MISFIRE_INSTRUCTION_SMART_POLICY;
}
