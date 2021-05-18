package com.gls.job.dashboard.web.converter;

import com.gls.job.dashboard.core.constants.QuartzConstants;
import com.gls.job.dashboard.web.entity.CalendarIntervalTriggerEntity;
import org.quartz.CalendarIntervalScheduleBuilder;
import org.quartz.CalendarIntervalTrigger;
import org.springframework.stereotype.Component;

import java.util.TimeZone;

/**
 * @author george
 */
@Component
public class CalendarIntervalTriggerConverter extends TriggerConverter<CalendarIntervalTrigger, CalendarIntervalTriggerEntity> {

    @Override
    protected CalendarIntervalTriggerEntity loadEntity(CalendarIntervalTrigger trigger) {
        CalendarIntervalTriggerEntity entity = new CalendarIntervalTriggerEntity();
        entity.setInterval(trigger.getRepeatInterval());
        entity.setIntervalUnit(trigger.getRepeatIntervalUnit());
        entity.setMisfireInstruction(loadMisfireInstruction(QuartzConstants.TRIGGER_TYPE_CALENDAR_INTERVAL, trigger.getMisfireInstruction()));
        entity.setTimeZone(trigger.getTimeZone().getID());
        entity.setPreserveHourOfDayAcrossDaylightSavings(trigger.isPreserveHourOfDayAcrossDaylightSavings());
        entity.setSkipDayIfHourDoesNotExist(trigger.isSkipDayIfHourDoesNotExist());
        return entity;
    }

    @Override
    protected CalendarIntervalScheduleBuilder loadScheduleBuilder(CalendarIntervalTriggerEntity entity) {
        CalendarIntervalScheduleBuilder scheduleBuilder = CalendarIntervalScheduleBuilder.calendarIntervalSchedule()
                .withInterval(entity.getInterval(), entity.getIntervalUnit())
                .inTimeZone(TimeZone.getTimeZone(entity.getTimeZone()))
                .preserveHourOfDayAcrossDaylightSavings(entity.getPreserveHourOfDayAcrossDaylightSavings())
                .skipDayIfHourDoesNotExist(entity.getSkipDayIfHourDoesNotExist());
        switch (entity.getMisfireInstruction()) {
            case CALENDAR_INTERVAL_MISFIRE_INSTRUCTION_DO_NOTHING:
                scheduleBuilder.withMisfireHandlingInstructionDoNothing();
                break;
            case CALENDAR_INTERVAL_MISFIRE_INSTRUCTION_FIRE_ONCE_NOW:
                scheduleBuilder.withMisfireHandlingInstructionFireAndProceed();
                break;
            case CALENDAR_INTERVAL_MISFIRE_INSTRUCTION_IGNORE_MISFIRE_POLICY:
                scheduleBuilder.withMisfireHandlingInstructionIgnoreMisfires();
                break;
            default:
                break;
        }
        return scheduleBuilder;
    }
}
