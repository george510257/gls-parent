package com.gls.job.dashboard.web.converter;

import com.gls.job.dashboard.web.entity.CalendarIntervalTriggerEntity;
import org.quartz.impl.triggers.CalendarIntervalTriggerImpl;
import org.springframework.stereotype.Component;

import java.util.TimeZone;

/**
 * @author george
 */
@Component
public class CalendarIntervalTriggerConverter extends TriggerConverter<CalendarIntervalTriggerEntity, CalendarIntervalTriggerImpl> {
    @Override
    public CalendarIntervalTriggerImpl copySourceToTarget(CalendarIntervalTriggerEntity entity, CalendarIntervalTriggerImpl trigger) {
        trigger.setRepeatIntervalUnit(entity.getIntervalUnit());
        trigger.setRepeatInterval(entity.getIntervalTime());
        trigger.setTimeZone(TimeZone.getTimeZone(entity.getTimeZone()));
        trigger.setPreserveHourOfDayAcrossDaylightSavings(entity.getPreserveHourOfDay());
        trigger.setSkipDayIfHourDoesNotExist(entity.getSkipDay());
        return super.copySourceToTarget(entity, trigger);
    }

    @Override
    public CalendarIntervalTriggerEntity copyTargetToSource(CalendarIntervalTriggerImpl trigger, CalendarIntervalTriggerEntity entity) {
        entity.setIntervalTime(trigger.getRepeatInterval());
        entity.setIntervalUnit(trigger.getRepeatIntervalUnit());
        entity.setTimeZone(trigger.getTimeZone().getID());
        entity.setPreserveHourOfDay(trigger.isPreserveHourOfDayAcrossDaylightSavings());
        entity.setSkipDay(trigger.isSkipDayIfHourDoesNotExist());
        return super.copyTargetToSource(trigger, entity);
    }
}
