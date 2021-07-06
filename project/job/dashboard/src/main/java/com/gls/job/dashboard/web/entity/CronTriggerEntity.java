package com.gls.job.dashboard.web.entity;

import com.gls.starter.data.jpa.annotations.Comment;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author george
 */
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@Entity
@Table(name = "CRON_TRIGGER")
@Comment("Cron触发器信息表")
public class CronTriggerEntity extends BaseTriggerEntity {
    @Comment("Cron表达式")
    private String cronExpression;
    @Comment("时区")
    private String timeZone;
}
