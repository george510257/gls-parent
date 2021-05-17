package com.gls.job.dashboard.web.entity;

import com.gls.job.dashboard.web.entity.enums.MisfireInstruction;
import com.gls.starter.data.jpa.annotations.Comment;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.quartz.DateBuilder;

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
@Comment("日历间隔触发器信息表")
public class CalendarIntervalTriggerEntity extends TriggerEntity {

    @Comment("间隔时间")
    private Integer interval = 1;

    @Comment("间隔单位")
    @Enumerated(EnumType.STRING)
    private DateBuilder.IntervalUnit intervalUnit = DateBuilder.IntervalUnit.DAY;

    @Comment("失败说明")
    @Enumerated(EnumType.STRING)
    private MisfireInstruction misfireInstruction = MisfireInstruction.CALENDAR_INTERVAL_MISFIRE_INSTRUCTION_SMART_POLICY;

    @Comment("时区")
    private String timeZone;

    @Comment("保留一天中的小时数，跨越一天的夏令时")
    private Boolean preserveHourOfDayAcrossDaylightSavings;

    @Comment("如果小时不存在，则跳过一天")
    private Boolean skipDayIfHourDoesNotExist;
}
