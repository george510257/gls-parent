package com.gls.job.dashboard.web.converter;

import com.gls.job.dashboard.core.constants.QuartzConstants;
import com.gls.job.dashboard.web.entity.DailyTimeIntervalTriggerEntity;
import org.quartz.DailyTimeIntervalScheduleBuilder;
import org.quartz.DailyTimeIntervalTrigger;
import org.quartz.TimeOfDay;
import org.quartz.impl.triggers.DailyTimeIntervalTriggerImpl;
import org.springframework.stereotype.Component;

import java.sql.Time;
import java.time.LocalTime;

/**
 * @author george
 */
@Component
public class DailyTimeIntervalTriggerConverter extends TriggerConverter<DailyTimeIntervalTrigger, DailyTimeIntervalTriggerEntity> {

    @Override
    protected DailyTimeIntervalTriggerEntity loadEntity(DailyTimeIntervalTrigger trigger) {
        DailyTimeIntervalTriggerEntity entity = new DailyTimeIntervalTriggerEntity();
        entity.setInterval(trigger.getRepeatInterval());
        entity.setIntervalUnit(trigger.getRepeatIntervalUnit());
        entity.setMisfireInstruction(loadMisfireInstruction(QuartzConstants.TRIGGER_TYPE_DAILY_TIME_INTERVAL, trigger.getMisfireInstruction()));
        entity.setRepeatCount(trigger.getRepeatCount());
        entity.setDaysOfWeek(trigger.getDaysOfWeek());
        entity.setStartTimeOfDay(getTime(trigger.getStartTimeOfDay()));
        entity.setEndTimeOfDay(getTime(trigger.getEndTimeOfDay()));
        return entity;
    }

    private Time getTime(TimeOfDay timeOfDay) {
        return Time.valueOf(LocalTime.of(timeOfDay.getHour(), timeOfDay.getMinute(), timeOfDay.getSecond()));
    }

    @Override
    protected DailyTimeIntervalTrigger loadTrigger(DailyTimeIntervalTriggerEntity entity) {
        DailyTimeIntervalTriggerImpl trigger = new DailyTimeIntervalTriggerImpl();
        trigger.setRepeatInterval(entity.getInterval());
        trigger.setRepeatIntervalUnit(entity.getIntervalUnit());
        trigger.setMisfireInstruction(entity.getMisfireInstruction().getCode());
        trigger.setRepeatCount(entity.getRepeatCount());
        if (entity.getDaysOfWeek() != null) {
            trigger.setDaysOfWeek(entity.getDaysOfWeek());
        } else {
            trigger.setDaysOfWeek(DailyTimeIntervalScheduleBuilder.ALL_DAYS_OF_THE_WEEK);
        }
        if (entity.getStartTimeOfDay() != null) {
            trigger.setStartTimeOfDay(getTimeOfDay(entity.getStartTimeOfDay()));
        } else {
            trigger.setStartTimeOfDay(TimeOfDay.hourAndMinuteOfDay(0, 0));
        }
        if (entity.getEndTimeOfDay() != null) {
            trigger.setEndTimeOfDay(getTimeOfDay(entity.getEndTimeOfDay()));
        } else {
            trigger.setEndTimeOfDay(TimeOfDay.hourMinuteAndSecondOfDay(23, 59, 59));
        }
        return trigger;
    }

    private TimeOfDay getTimeOfDay(Time time) {
        return new TimeOfDay(time.toLocalTime().getHour(), time.toLocalTime().getMinute(), time.toLocalTime().getSecond());
    }
}
