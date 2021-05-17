package com.gls.job.dashboard.web.converter;

import com.gls.framework.core.base.BaseConverter;
import com.gls.job.dashboard.web.entity.JobDetailEntity;
import com.gls.job.dashboard.web.entity.TriggerEntity;
import com.gls.job.dashboard.web.entity.enums.MisfireInstruction;
import org.quartz.JobDataMap;
import org.quartz.JobKey;
import org.quartz.TriggerKey;
import org.quartz.spi.OperableTrigger;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @author george
 */
public abstract class TriggerConverter<Trigger extends org.quartz.Trigger, Entity extends TriggerEntity> extends BaseConverter<Trigger, Entity> {
    @Override
    protected Entity copySourceToTarget(Trigger trigger) {
        Entity entity = loadEntity(trigger);
        entity.setName(trigger.getKey().getName());
        entity.setGroup(trigger.getKey().getGroup());
        entity.setDescription(trigger.getDescription());
        entity.setStartTime(trigger.getStartTime());
        entity.setEndTime(trigger.getEndTime());
        entity.setPriority(trigger.getPriority());
        entity.setCalendarName(trigger.getCalendarName());
        entity.setJobDetail(loadJobDetailEntity(trigger.getJobKey()));
        return entity;
    }

    private JobDetailEntity loadJobDetailEntity(JobKey jobKey) {
        return null;
    }

    protected MisfireInstruction loadMisfireInstruction(String type, int code) {
        return Arrays.stream(MisfireInstruction.values()).filter(misfireInstruction ->
                type.equals(misfireInstruction.getType()) && misfireInstruction.getCode() == code
        ).collect(Collectors.toList()).get(0);
    }

    /**
     * 加载 Entity
     *
     * @param trigger
     * @return
     */
    protected abstract Entity loadEntity(Trigger trigger);

    @Override
    protected Trigger copyTargetToSource(Entity entity) {
        Trigger trigger = loadTrigger(entity);
        if (trigger instanceof OperableTrigger) {
            OperableTrigger trigger1 = (OperableTrigger) trigger;

            trigger1.setCalendarName(entity.getCalendarName());
            trigger1.setDescription(entity.getDescription());
            trigger1.setStartTime(entity.getStartTime());
            trigger1.setEndTime(entity.getEndTime());
            trigger1.setKey(TriggerKey.triggerKey(entity.getName(), entity.getGroup()));
            if (entity.getJobDetail() != null) {
                trigger1.setJobKey(JobKey.jobKey(entity.getName(), entity.getGroup()));
                if (!entity.getJobDetail().getJobDataMap().isEmpty()) {
                    trigger1.setJobDataMap(new JobDataMap(entity.getJobDetail().getJobDataMap()));
                }
            }
            trigger1.setPriority(entity.getPriority());

            return (Trigger) trigger1;
        }
        return trigger;
    }

    /**
     * 加载 Trigger
     *
     * @param entity
     * @return
     */
    protected abstract Trigger loadTrigger(Entity entity);
}
