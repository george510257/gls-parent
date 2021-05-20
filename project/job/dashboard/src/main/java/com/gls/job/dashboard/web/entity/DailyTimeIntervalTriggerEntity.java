package com.gls.job.dashboard.web.entity;

import com.gls.job.dashboard.web.entity.enums.MisfireInstruction;
import com.gls.starter.data.jpa.annotations.Comment;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.quartz.DateBuilder;

import javax.persistence.*;
import java.sql.Time;
import java.util.Set;

/**
 * @author george
 */
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "DAILY_TIME_INTERVAL_TRIGGER")
@Comment("每日时间间隔触发器信息表")
public class DailyTimeIntervalTriggerEntity extends TriggerEntity {

    @Comment("间隔时间")
    private Integer intervalTime = 1;

    @Comment("间隔单位")
    @Enumerated(EnumType.STRING)
    private DateBuilder.IntervalUnit intervalUnit = DateBuilder.IntervalUnit.MINUTE;

    @Comment("一周中的几天")
    @ElementCollection
    private Set<Integer> daysOfWeek;

    @Comment("开始时间")
    private Time startTimeOfDay;

    @Comment("结束时间")
    private Time endTimeOfDay;

    @Comment("重复次数")
    private Integer repeatCount = -1;

    @Comment("失败说明")
    @Enumerated(EnumType.STRING)
    private MisfireInstruction misfireInstruction = MisfireInstruction.DAILY_TIME_INTERVAL_MISFIRE_INSTRUCTION_SMART_POLICY;
}
