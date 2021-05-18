package com.gls.job.dashboard.web.converter;

import com.gls.framework.core.base.BaseConverter;
import com.gls.job.dashboard.web.entity.JobDetailEntity;
import com.gls.job.dashboard.web.entity.TriggerEntity;
import com.gls.job.dashboard.web.entity.enums.MisfireInstruction;
import org.quartz.JobDataMap;
import org.quartz.JobKey;
import org.quartz.ScheduleBuilder;
import org.quartz.TriggerBuilder;

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
        return TriggerBuilder.newTrigger()
                .withIdentity(entity.getName(), entity.getGroup())
                .withDescription(entity.getDescription())
                .withPriority(entity.getPriority())
                .withSchedule(loadScheduleBuilder(entity))
                .modifiedByCalendar(entity.getCalendarName())
                .startAt(entity.getStartTime())
                .endAt(entity.getEndTime())
                .forJob(entity.getJobDetail().getName(), entity.getJobDetail().getGroup())
                .usingJobData(new JobDataMap(entity.getJobDetail().getJobDataMap()))
                .build();
    }

    /**
     * 加载 Trigger
     *
     * @param entity
     * @return
     */
    protected abstract ScheduleBuilder<Trigger> loadScheduleBuilder(Entity entity);

}
