package com.gls.job.dashboard.web.converter;

import com.gls.job.dashboard.core.constants.QuartzConstants;
import com.gls.job.dashboard.web.entity.CronTriggerEntity;
import org.quartz.CronExpression;
import org.quartz.CronTrigger;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.springframework.stereotype.Component;

import java.text.ParseException;
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
    protected CronTrigger loadTrigger(CronTriggerEntity entity) {
        CronTriggerImpl trigger = new CronTriggerImpl();
        CronExpression cronExpression = null;
        try {
            cronExpression = new CronExpression(entity.getCronExpression());
            cronExpression.setTimeZone(TimeZone.getTimeZone(entity.getTimeZone()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        trigger.setCronExpression(cronExpression);
        trigger.setTimeZone(cronExpression.getTimeZone());
        trigger.setMisfireInstruction(entity.getMisfireInstruction().getCode());
        return trigger;
    }
}
