package com.gls.job.dashboard.web.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.quartz.CalendarIntervalTrigger;
import org.quartz.CronTrigger;
import org.quartz.DailyTimeIntervalTrigger;
import org.quartz.SimpleTrigger;
import org.quartz.impl.triggers.*;

/**
 * @author george
 */
@Getter
@AllArgsConstructor
public enum MisfireInstruction {
    /**
     * CalendarInterval
     */
    CALENDAR_INTERVAL_SMART_POLICY(CalendarIntervalTriggerImpl.class, CalendarIntervalTrigger.MISFIRE_INSTRUCTION_SMART_POLICY),
    CALENDAR_INTERVAL_IGNORE_MISFIRE_POLICY(CalendarIntervalTriggerImpl.class, CalendarIntervalTrigger.MISFIRE_INSTRUCTION_IGNORE_MISFIRE_POLICY),
    CALENDAR_INTERVAL_DO_NOTHING(CalendarIntervalTriggerImpl.class, CalendarIntervalTrigger.MISFIRE_INSTRUCTION_DO_NOTHING),
    CALENDAR_INTERVAL_FIRE_ONCE_NOW(CalendarIntervalTriggerImpl.class, CalendarIntervalTrigger.MISFIRE_INSTRUCTION_FIRE_ONCE_NOW),
    /**
     * Cron
     */
    CRON_SMART_POLICY(CronTriggerImpl.class, CronTrigger.MISFIRE_INSTRUCTION_SMART_POLICY),
    CRON_IGNORE_MISFIRE_POLICY(CronTriggerImpl.class, CronTrigger.MISFIRE_INSTRUCTION_IGNORE_MISFIRE_POLICY),
    CRON_DO_NOTHING(CronTriggerImpl.class, CronTrigger.MISFIRE_INSTRUCTION_DO_NOTHING),
    CRON_FIRE_ONCE_NOW(CronTriggerImpl.class, CronTrigger.MISFIRE_INSTRUCTION_FIRE_ONCE_NOW),
    /**
     * DailyTimeInterval
     */
    DAILY_TIME_INTERVAL_SMART_POLICY(DailyTimeIntervalTriggerImpl.class, DailyTimeIntervalTrigger.MISFIRE_INSTRUCTION_SMART_POLICY),
    DAILY_TIME_INTERVAL_IGNORE_MISFIRE_POLICY(DailyTimeIntervalTriggerImpl.class, DailyTimeIntervalTrigger.MISFIRE_INSTRUCTION_IGNORE_MISFIRE_POLICY),
    DAILY_TIME_INTERVAL_DO_NOTHING(DailyTimeIntervalTriggerImpl.class, DailyTimeIntervalTrigger.MISFIRE_INSTRUCTION_DO_NOTHING),
    DAILY_TIME_INTERVAL_FIRE_ONCE_NOW(DailyTimeIntervalTriggerImpl.class, DailyTimeIntervalTrigger.MISFIRE_INSTRUCTION_FIRE_ONCE_NOW),
    /**
     * Simple
     */
    SIMPLE_SMART_POLICY(SimpleTriggerImpl.class, SimpleTrigger.MISFIRE_INSTRUCTION_SMART_POLICY),
    SIMPLE_IGNORE_MISFIRE_POLICY(SimpleTriggerImpl.class, SimpleTrigger.MISFIRE_INSTRUCTION_IGNORE_MISFIRE_POLICY),
    SIMPLE_FIRE_NOW(SimpleTriggerImpl.class, SimpleTrigger.MISFIRE_INSTRUCTION_FIRE_NOW),
    SIMPLE_RESCHEDULE_NOW_WITH_EXISTING_REPEAT_COUNT(SimpleTriggerImpl.class, SimpleTrigger.MISFIRE_INSTRUCTION_RESCHEDULE_NOW_WITH_EXISTING_REPEAT_COUNT),
    SIMPLE_RESCHEDULE_NOW_WITH_REMAINING_REPEAT_COUNT(SimpleTriggerImpl.class, SimpleTrigger.MISFIRE_INSTRUCTION_RESCHEDULE_NOW_WITH_REMAINING_REPEAT_COUNT),
    SIMPLE_RESCHEDULE_NEXT_WITH_REMAINING_COUNT(SimpleTriggerImpl.class, SimpleTrigger.MISFIRE_INSTRUCTION_RESCHEDULE_NEXT_WITH_REMAINING_COUNT),
    SIMPLE_RESCHEDULE_NEXT_WITH_EXISTING_COUNT(SimpleTriggerImpl.class, SimpleTrigger.MISFIRE_INSTRUCTION_RESCHEDULE_NEXT_WITH_EXISTING_COUNT),
    ;
    private final Class<? extends AbstractTrigger> typeClass;
    private final int code;
}
