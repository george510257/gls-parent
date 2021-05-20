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
        entity.setIntervalTime(trigger.getRepeatInterval());
        entity.setIntervalUnit(trigger.getRepeatIntervalUnit());
        entity.setMisfireInstruction(loadMisfireInstruction(QuartzConstants.TRIGGER_TYPE_CALENDAR_INTERVAL, trigger.getMisfireInstruction()));
        entity.setTimeZone(trigger.getTimeZone().getID());
        entity.setPreserveHourOfDay(trigger.isPreserveHourOfDayAcrossDaylightSavings());
        entity.setSkipDay(trigger.isSkipDayIfHourDoesNotExist());
        return entity;
    }

    @Override
    protected CalendarIntervalScheduleBuilder loadScheduleBuilder(CalendarIntervalTriggerEntity entity) {
        CalendarIntervalScheduleBuilder scheduleBuilder = CalendarIntervalScheduleBuilder.calendarIntervalSchedule()
                .withInterval(entity.getIntervalTime(), entity.getIntervalUnit())
                .inTimeZone(TimeZone.getTimeZone(entity.getTimeZone()))
                .preserveHourOfDayAcrossDaylightSavings(entity.getPreserveHourOfDay())
                .skipDayIfHourDoesNotExist(entity.getSkipDay());
        loadMisfireInstruction(scheduleBuilder, entity.getMisfireInstruction());
        return scheduleBuilder;
    }
}
