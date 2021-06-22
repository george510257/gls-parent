package com.gls.job.dashboard.web.converter;

import com.gls.job.dashboard.web.entity.SimpleTriggerEntity;
import org.quartz.impl.triggers.SimpleTriggerImpl;
import org.springframework.stereotype.Component;

/**
 * @author george
 */
@Component
public class SimpleTriggerConverter extends TriggerConverter<SimpleTriggerEntity, SimpleTriggerImpl> {
    @Override
    public SimpleTriggerImpl copySourceToTarget(SimpleTriggerEntity entity, SimpleTriggerImpl trigger) {
        trigger.setRepeatInterval(entity.getIntervalTime());
        trigger.setRepeatCount(entity.getRepeatCount());
        return super.copySourceToTarget(entity, trigger);
    }

    @Override
    public SimpleTriggerEntity copyTargetToSource(SimpleTriggerImpl trigger, SimpleTriggerEntity entity) {
        entity.setIntervalTime(trigger.getRepeatInterval());
        entity.setRepeatCount(trigger.getRepeatCount());
        return super.copyTargetToSource(trigger, entity);
    }

}
