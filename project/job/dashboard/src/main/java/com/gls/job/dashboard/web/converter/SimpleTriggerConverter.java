package com.gls.job.dashboard.web.converter;

import com.gls.job.dashboard.core.constants.QuartzConstants;
import com.gls.job.dashboard.web.entity.SimpleTriggerEntity;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.SimpleTrigger;
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
    protected SimpleScheduleBuilder loadScheduleBuilder(SimpleTriggerEntity entity) {
        SimpleScheduleBuilder simpleScheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
                .withIntervalInMilliseconds(entity.getInterval())
                .withRepeatCount(entity.getRepeatCount());
        switch (entity.getMisfireInstruction()) {
            case SIMPLE_MISFIRE_INSTRUCTION_FIRE_NOW:
                simpleScheduleBuilder.withMisfireHandlingInstructionFireNow();
                break;
            case SIMPLE_MISFIRE_INSTRUCTION_IGNORE_MISFIRE_POLICY:
                simpleScheduleBuilder.withMisfireHandlingInstructionIgnoreMisfires();
                break;
            case SIMPLE_MISFIRE_INSTRUCTION_RESCHEDULE_NEXT_WITH_EXISTING_COUNT:
                simpleScheduleBuilder.withMisfireHandlingInstructionNextWithExistingCount();
                break;
            case SIMPLE_MISFIRE_INSTRUCTION_RESCHEDULE_NEXT_WITH_REMAINING_COUNT:
                simpleScheduleBuilder.withMisfireHandlingInstructionNextWithRemainingCount();
                break;
            case SIMPLE_MISFIRE_INSTRUCTION_RESCHEDULE_NOW_WITH_EXISTING_REPEAT_COUNT:
                simpleScheduleBuilder.withMisfireHandlingInstructionNowWithExistingCount();
                break;
            case SIMPLE_MISFIRE_INSTRUCTION_RESCHEDULE_NOW_WITH_REMAINING_REPEAT_COUNT:
                simpleScheduleBuilder.withMisfireHandlingInstructionNowWithRemainingCount();
                break;
            default:
                break;
        }
        return simpleScheduleBuilder;
    }

}
