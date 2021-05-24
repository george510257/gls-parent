package com.gls.job.dashboard.web.converter;

import com.gls.job.dashboard.core.constants.QuartzConstants;
import com.gls.job.dashboard.web.entity.SimpleTriggerEntity;
import com.gls.job.dashboard.web.repository.SimpleTriggerRepository;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.SimpleTrigger;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author george
 */
@Component
public class SimpleTriggerConverter extends TriggerConverter<SimpleTrigger, SimpleTriggerEntity> {

    @Resource
    private SimpleTriggerRepository simpleTriggerRepository;

    @Override
    protected SimpleTriggerEntity loadEntity(SimpleTrigger trigger) {
        SimpleTriggerEntity entity = simpleTriggerRepository.findByNameAndGroupName(trigger.getKey().getName(), trigger.getKey().getGroup());
        entity.setIntervalTime(trigger.getRepeatInterval());
        entity.setRepeatCount(trigger.getRepeatCount());
        entity.setMisfireInstruction(loadMisfireInstruction(QuartzConstants.TRIGGER_TYPE_SIMPLE, trigger.getMisfireInstruction()));
        return entity;
    }

    @Override
    protected SimpleScheduleBuilder loadScheduleBuilder(SimpleTriggerEntity entity) {
        SimpleScheduleBuilder simpleScheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
                .withIntervalInMilliseconds(entity.getIntervalTime())
                .withRepeatCount(entity.getRepeatCount());
        loadMisfireInstruction(simpleScheduleBuilder, entity.getMisfireInstruction());
        return simpleScheduleBuilder;
    }

}
