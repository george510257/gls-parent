package com.gls.job.dashboard.web.converter;

import com.gls.job.dashboard.web.entity.CronTriggerEntity;
import lombok.extern.slf4j.Slf4j;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.TimeZone;

/**
 * @author george
 */
@Slf4j
@Component
public class CronTriggerConverter extends BaseTriggerConverter<CronTriggerEntity, CronTriggerImpl> {
    @Override
    public CronTriggerImpl copySourceToTarget(CronTriggerEntity entity, CronTriggerImpl trigger) {
        try {
            trigger.setCronExpression(entity.getCronExpression());
        } catch (ParseException e) {
            log.error(e.getMessage(), e);
        }
        trigger.setTimeZone(TimeZone.getTimeZone(entity.getTimeZone()));
        return super.copySourceToTarget(entity, trigger);
    }

    @Override
    public CronTriggerEntity copyTargetToSource(CronTriggerImpl trigger, CronTriggerEntity entity) {
        entity.setCronExpression(trigger.getCronExpression());
        entity.setTimeZone(trigger.getTimeZone().getID());
        return super.copyTargetToSource(trigger, entity);
    }
}
