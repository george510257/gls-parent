package com.gls.job.dashboard.web.converter;

import com.gls.job.dashboard.core.constants.QuartzConstants;
import com.gls.job.dashboard.web.entity.DailyTimeIntervalTriggerEntity;
import com.gls.job.dashboard.web.repository.DailyTimeIntervalTriggerRepository;
import org.quartz.DailyTimeIntervalScheduleBuilder;
import org.quartz.DailyTimeIntervalTrigger;
import org.quartz.TimeOfDay;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.sql.Time;
import java.time.LocalTime;

/**
 * @author george
 */
@Component
public class DailyTimeIntervalTriggerConverter extends TriggerConverter<DailyTimeIntervalTrigger, DailyTimeIntervalTriggerEntity> {

    @Resource
    private DailyTimeIntervalTriggerRepository dailyTimeIntervalTriggerRepository;

    @Override
    protected DailyTimeIntervalTriggerEntity loadEntity(DailyTimeIntervalTrigger trigger) {
        DailyTimeIntervalTriggerEntity entity = dailyTimeIntervalTriggerRepository.findByNameAndGroupName(trigger.getKey().getName(), trigger.getKey().getGroup());
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
