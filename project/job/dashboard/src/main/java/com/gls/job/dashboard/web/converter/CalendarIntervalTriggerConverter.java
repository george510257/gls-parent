package com.gls.job.dashboard.web.converter;

import com.gls.job.dashboard.core.constants.QuartzConstants;
import com.gls.job.dashboard.web.entity.CalendarIntervalTriggerEntity;
import com.gls.job.dashboard.web.repository.CalendarIntervalTriggerRepository;
import org.quartz.CalendarIntervalScheduleBuilder;
import org.quartz.CalendarIntervalTrigger;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.TimeZone;

/**
 * @author george
 */
@Component
public class CalendarIntervalTriggerConverter extends TriggerConverter<CalendarIntervalTriggerEntity, CalendarIntervalTrigger> {

    @Resource
    private CalendarIntervalTriggerRepository calendarIntervalTriggerRepository;

    @Override
    protected CalendarIntervalTriggerEntity loadEntity(CalendarIntervalTrigger trigger) {
        CalendarIntervalTriggerEntity entity = calendarIntervalTriggerRepository.findByNameAndGroupName(trigger.getKey().getName(), trigger.getKey().getGroup());
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
