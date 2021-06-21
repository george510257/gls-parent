package com.gls.job.dashboard.web.converter;

import com.gls.job.dashboard.core.constants.QuartzConstants;
import com.gls.job.dashboard.web.entity.CronTriggerEntity;
import com.gls.job.dashboard.web.repository.CronTriggerRepository;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.TimeZone;

/**
 * @author george
 */
@Component
public class CronTriggerConverter extends TriggerConverter<CronTriggerEntity, CronTrigger> {

    @Resource
    private CronTriggerRepository cronTriggerRepository;

    @Override
    protected CronTriggerEntity loadEntity(CronTrigger trigger) {
        CronTriggerEntity entity = cronTriggerRepository.findByNameAndGroupName(trigger.getKey().getName(), trigger.getKey().getGroup());
        entity.setCronExpression(trigger.getCronExpression());
        entity.setTimeZone(trigger.getTimeZone().getID());
        entity.setMisfireInstruction(loadMisfireInstruction(QuartzConstants.TRIGGER_TYPE_CRON, trigger.getMisfireInstruction()));
        return entity;
    }

    @Override
    protected CronScheduleBuilder loadScheduleBuilder(CronTriggerEntity entity) {
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(entity.getCronExpression())
                .inTimeZone(TimeZone.getTimeZone(entity.getTimeZone()));
        loadMisfireInstruction(scheduleBuilder, entity.getMisfireInstruction());
        return scheduleBuilder;
    }
}
