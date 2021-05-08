package com.gls.job.dashboard.web.entity;

import com.gls.job.dashboard.web.entity.enums.MisfireInstruction;
import com.gls.starter.data.jpa.annotations.Comment;
import com.gls.starter.data.jpa.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.quartz.DateBuilder;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.sql.Time;
import java.util.HashSet;
import java.util.Set;
import java.util.TimeZone;

/**
 * @author george
 */
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Comment("触发器信息表")
public class TriggerEntity extends BaseEntity {

    @Comment("触发器组")
    private String group;

    @Comment("触发失败状态码")
    @Enumerated(value = EnumType.STRING)
    private MisfireInstruction misfireInstruction;

    @Comment("间隔时间")
    private Long interval;

    @Comment("间隔单位")
    @Enumerated(value = EnumType.STRING)
    private DateBuilder.IntervalUnit intervalUnit;

    @Comment("重复次数")
    private Integer repeatCount;

    // CalendarInterval

    @Comment("执行时区")
    private TimeZone timeZone;

    @Comment("是否支持夏令时自动转换")
    private Boolean preserveHourOfDayAcrossDaylightSavings;

    @Comment("是否支持不存在时间跳过")
    private Boolean skipDayIfHourDoesNotExist;

    // Cron

    @Comment("Cron表达式")
    private String cronExpression;

    // DailyTimeInterval

    @Comment("一周中的几天")
    @ElementCollection
    private Set<Integer> daysOfWeek = new HashSet<>();

    @Comment("一天的开始时间")
    private Time startTimeOfDay;

    @Comment("一天的结束时间")
    private Time endTimeOfDay;

}
