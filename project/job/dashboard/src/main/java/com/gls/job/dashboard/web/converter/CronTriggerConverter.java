package com.gls.job.dashboard.web.converter;

import com.gls.job.dashboard.core.constants.QuartzConstants;
import com.gls.job.dashboard.web.entity.CronTriggerEntity;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.springframework.stereotype.Component;

import java.util.TimeZone;

/**
 * @author george
 */
@Component
public class CronTriggerConverter extends TriggerConverter<CronTrigger, CronTriggerEntity> {
    @Override
    protected CronTriggerEntity loadEntity(CronTrigger trigger) {
        CronTriggerEntity entity = new CronTriggerEntity();
        entity.setCronExpression(trigger.getCronExpression());
        entity.setTimeZone(trigger.getTimeZone().getID());
        entity.setMisfireInstruction(loadMisfireInstruction(QuartzConstants.TRIGGER_TYPE_CRON, trigger.getMisfireInstruction()));
        return entity;
    }

    @Override
    protected CronScheduleBuilder loadScheduleBuilder(CronTriggerEntity entity) {
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(entity.getCronExpression())
                .inTimeZone(TimeZone.getTimeZone(entity.getTimeZone()));
        switch (entity.getMisfireInstruction()) {
            case CRON_MISFIRE_INSTRUCTION_DO_NOTHING:
                scheduleBuilder.withMisfireHandlingInstructionDoNothing();
                break;
            case CRON_MISFIRE_INSTRUCTION_FIRE_ONCE_NOW:
                scheduleBuilder.withMisfireHandlingInstructionFireAndProceed();
                break;
            case CRON_MISFIRE_INSTRUCTION_IGNORE_MISFIRE_POLICY:
                scheduleBuilder.withMisfireHandlingInstructionIgnoreMisfires();
                break;
            default:
                break;
        }
        return scheduleBuilder;
    }
}
