package com.gls.job.dashboard.web.converter;

import com.gls.job.dashboard.core.constants.QuartzConstants;
import com.gls.job.dashboard.web.entity.DailyTimeIntervalTriggerEntity;
import org.quartz.DailyTimeIntervalScheduleBuilder;
import org.quartz.DailyTimeIntervalTrigger;
import org.quartz.TimeOfDay;
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
        entity.setIntervalTime(trigger.getRepeatInterval());
        entity.setIntervalUnit(trigger.getRepeatIntervalUnit());
        entity.setMisfireInstruction(loadMisfireInstruction(QuartzConstants.TRIGGER_TYPE_DAILY_TIME_INTERVAL, trigger.getMisfireInstruction()));
        entity.setRepeatCount(trigger.getRepeatCount());
        entity.setDaysOfWeek(trigger.getDaysOfWeek());
        entity.setStartTimeOfDay(getTime(trigger.getStartTimeOfDay()));
        entity.setEndTimeOfDay(getTime(trigger.getEndTimeOfDay()));
        return entity;
    }

    @Override
    protected DailyTimeIntervalScheduleBuilder loadScheduleBuilder(DailyTimeIntervalTriggerEntity entity) {
        DailyTimeIntervalScheduleBuilder scheduleBuilder = DailyTimeIntervalScheduleBuilder.dailyTimeIntervalSchedule()
                .withInterval(entity.getIntervalTime(), entity.getIntervalUnit())
                .withRepeatCount(entity.getRepeatCount())
                .onDaysOfTheWeek(entity.getDaysOfWeek())
                .startingDailyAt(getTimeOfDay(entity.getStartTimeOfDay()))
                .endingDailyAt(getTimeOfDay(entity.getEndTimeOfDay()));
        loadMisfireInstruction(scheduleBuilder, entity.getMisfireInstruction());
        return scheduleBuilder;
    }

    private Time getTime(TimeOfDay timeOfDay) {
        return Time.valueOf(LocalTime.of(timeOfDay.getHour(), timeOfDay.getMinute(), timeOfDay.getSecond()));
    }

    private TimeOfDay getTimeOfDay(Time time) {
        return TimeOfDay.hourAndMinuteAndSecondFromDate(time);
    }
}
