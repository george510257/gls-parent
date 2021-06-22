package com.gls.job.dashboard.web.converter;

import com.gls.framework.core.base.BaseConverter;
import com.gls.job.dashboard.web.entity.JobDetailEntity;
import com.gls.job.dashboard.web.entity.TriggerEntity;
import com.gls.job.dashboard.web.entity.enums.MisfireInstruction;
import com.gls.job.dashboard.web.repository.JobDetailRepository;
import org.quartz.JobDataMap;
import org.quartz.JobKey;
import org.quartz.TriggerKey;
import org.quartz.impl.triggers.AbstractTrigger;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @author george
 */
public class TriggerConverter<Entity extends TriggerEntity, Trigger extends AbstractTrigger> implements BaseConverter<Entity, Trigger> {

    @Resource
    private JobDetailRepository jobDetailRepository;

    @Override
    public Trigger copySourceToTarget(Entity entity, Trigger trigger) {
        trigger.setKey(new TriggerKey(entity.getName(), entity.getGroupName()));
        trigger.setJobKey(new JobKey(entity.getJobDetail().getName(), entity.getJobDetail().getGroupName()));
        trigger.setDescription(entity.getDescription());
        trigger.setCalendarName(entity.getCalendarName());
        trigger.setJobDataMap(new JobDataMap(entity.getJobDetail().getJobDataMap()));
        trigger.setPriority(entity.getPriority());
        trigger.setStartTime(entity.getStartTime());
        trigger.setEndTime(entity.getEndTime());
        trigger.setMisfireInstruction(getMisfireInstruction(entity.getMisfireInstruction()));
        return trigger;
    }

    @Override
    public Entity copyTargetToSource(Trigger trigger, Entity entity) {
        entity.setMisfireInstruction(getMisfireInstruction(trigger.getClass(), trigger.getMisfireInstruction()));
        entity.setGroupName(trigger.getKey().getGroup());
        entity.setDescription(trigger.getDescription());
        entity.setStartTime(trigger.getStartTime());
        entity.setEndTime(trigger.getEndTime());
        entity.setPriority(trigger.getPriority());
        entity.setCalendarName(trigger.getCalendarName());
        entity.setJobDetail(getJobDetail(trigger.getJobKey()));
        entity.setName(trigger.getKey().getName());
        return entity;
    }

    private JobDetailEntity getJobDetail(JobKey jobKey) {
        return jobDetailRepository.findByNameAndGroupName(jobKey.getName(), jobKey.getGroup());
    }

    private MisfireInstruction getMisfireInstruction(Class<? extends AbstractTrigger> type, int code) {
        return Arrays.stream(MisfireInstruction.values()).filter(misfireInstruction ->
                type.equals(misfireInstruction.getTypeClass()) && misfireInstruction.getCode() == code
        ).collect(Collectors.toList()).get(0);

    }

    private int getMisfireInstruction(MisfireInstruction misfireInstruction) {
        return misfireInstruction.getCode();
    }
}
