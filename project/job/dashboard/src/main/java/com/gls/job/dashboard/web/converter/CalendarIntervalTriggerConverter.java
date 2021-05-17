package com.gls.job.dashboard.web.converter;

import com.gls.job.dashboard.core.constants.QuartzConstants;
import com.gls.job.dashboard.web.entity.CalendarIntervalTriggerEntity;
import org.quartz.CalendarIntervalTrigger;
import org.quartz.impl.triggers.CalendarIntervalTriggerImpl;
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
    protected CalendarIntervalTrigger loadTrigger(CalendarIntervalTriggerEntity entity) {
        CalendarIntervalTriggerImpl trigger = new CalendarIntervalTriggerImpl();
        trigger.setRepeatInterval(entity.getInterval());
        trigger.setRepeatIntervalUnit(entity.getIntervalUnit());
        trigger.setMisfireInstruction(entity.getMisfireInstruction().getCode());
        trigger.setTimeZone(TimeZone.getTimeZone(entity.getTimeZone()));
        trigger.setPreserveHourOfDayAcrossDaylightSavings(entity.getPreserveHourOfDayAcrossDaylightSavings());
        trigger.setSkipDayIfHourDoesNotExist(entity.getSkipDayIfHourDoesNotExist());
        return trigger;
    }
}
