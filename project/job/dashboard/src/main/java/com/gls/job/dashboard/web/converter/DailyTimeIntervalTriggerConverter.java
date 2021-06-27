package com.gls.job.dashboard.web.converter;

import com.gls.job.dashboard.web.entity.DailyTimeIntervalTriggerEntity;
import org.quartz.TimeOfDay;
import org.quartz.impl.triggers.DailyTimeIntervalTriggerImpl;
import org.springframework.stereotype.Component;

import java.sql.Time;
import java.time.LocalTime;

/**
 * @author george
 */
@Component
public class DailyTimeIntervalTriggerConverter extends TriggerConverter<DailyTimeIntervalTriggerEntity, DailyTimeIntervalTriggerImpl> {
    @Override
    public DailyTimeIntervalTriggerImpl copySourceToTarget(DailyTimeIntervalTriggerEntity entity, DailyTimeIntervalTriggerImpl trigger) {
        trigger.setRepeatInterval(entity.getIntervalTime());
        trigger.setRepeatIntervalUnit(entity.getIntervalUnit());
        trigger.setRepeatCount(entity.getRepeatCount());
        trigger.setDaysOfWeek(entity.getDaysOfWeek());
        trigger.setStartTimeOfDay(getTimeOfDay(entity.getStartTimeOfDay()));
        trigger.setEndTimeOfDay(getTimeOfDay(entity.getEndTimeOfDay()));
        return super.copySourceToTarget(entity, trigger);
    }

    @Override
    public DailyTimeIntervalTriggerEntity copyTargetToSource(DailyTimeIntervalTriggerImpl trigger, DailyTimeIntervalTriggerEntity entity) {
        entity.setIntervalTime(trigger.getRepeatInterval());
        entity.setIntervalUnit(trigger.getRepeatIntervalUnit());
        entity.setRepeatCount(trigger.getRepeatCount());
        entity.setDaysOfWeek(trigger.getDaysOfWeek());
        entity.setStartTimeOfDay(getTime(trigger.getStartTimeOfDay()));
        entity.setEndTimeOfDay(getTime(trigger.getEndTimeOfDay()));
        return super.copyTargetToSource(trigger, entity);
    }

    private Time getTime(TimeOfDay timeOfDay) {
        return Time.valueOf(LocalTime.of(timeOfDay.getHour(), timeOfDay.getMinute(), timeOfDay.getSecond()));
    }

    private TimeOfDay getTimeOfDay(Time time) {
        return TimeOfDay.hourAndMinuteAndSecondFromDate(time);
    }
}
