package com.gls.job.dashboard.web.converter;

import com.gls.job.dashboard.core.constants.QuartzConstants;
import com.gls.job.dashboard.web.entity.SimpleTriggerEntity;
import org.quartz.SimpleTrigger;
import org.quartz.impl.triggers.SimpleTriggerImpl;
import org.springframework.stereotype.Component;

/**
 * @author george
 */
@Component
public class SimpleTriggerConverter extends TriggerConverter<SimpleTrigger, SimpleTriggerEntity> {

    @Override
    protected SimpleTriggerEntity loadEntity(SimpleTrigger trigger) {
        SimpleTriggerEntity entity = new SimpleTriggerEntity();
        entity.setInterval(trigger.getRepeatInterval());
        entity.setRepeatCount(trigger.getRepeatCount());
        entity.setMisfireInstruction(loadMisfireInstruction(QuartzConstants.TRIGGER_TYPE_SIMPLE, trigger.getMisfireInstruction()));
        return entity;
    }

    @Override
    protected SimpleTrigger loadTrigger(SimpleTriggerEntity entity) {
        SimpleTriggerImpl trigger = new SimpleTriggerImpl();
        trigger.setRepeatInterval(entity.getInterval());
        trigger.setRepeatCount(entity.getRepeatCount());
        trigger.setMisfireInstruction(entity.getMisfireInstruction().getCode());
        return trigger;
    }
}
