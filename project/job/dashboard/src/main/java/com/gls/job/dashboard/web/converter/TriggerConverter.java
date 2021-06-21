package com.gls.job.dashboard.web.converter;

import com.gls.framework.core.base.BaseConverter;
import com.gls.job.dashboard.web.entity.JobDetailEntity;
import com.gls.job.dashboard.web.entity.TriggerEntity;
import com.gls.job.dashboard.web.entity.enums.MisfireInstruction;
import com.gls.job.dashboard.web.repository.JobDetailRepository;
import org.quartz.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @author george
 */
public abstract class TriggerConverter<Entity extends TriggerEntity, Trigger extends org.quartz.Trigger> implements BaseConverter<Entity, Trigger> {

    @Resource
    private JobDetailRepository jobDetailRepository;

//    @Override
//    protected Entity copySourceToTarget(Trigger trigger) {
//        Entity entity = loadEntity(trigger);
//        entity.setName(trigger.getKey().getName());
//        entity.setGroupName(trigger.getKey().getGroup());
//        entity.setDescription(trigger.getDescription());
//        entity.setStartTime(trigger.getStartTime());
//        entity.setEndTime(trigger.getEndTime());
//        entity.setPriority(trigger.getPriority());
//        entity.setCalendarName(trigger.getCalendarName());
//        entity.setJobDetail(loadJobDetailEntity(trigger.getJobKey()));
//        return entity;
//    }

    private JobDetailEntity loadJobDetailEntity(JobKey jobKey) {
        return jobDetailRepository.findByNameAndGroupName(jobKey.getName(), jobKey.getGroup());
    }

    protected MisfireInstruction loadMisfireInstruction(String type, int code) {
        return Arrays.stream(MisfireInstruction.values()).filter(misfireInstruction ->
                type.equals(misfireInstruction.getType()) && misfireInstruction.getCode() == code
        ).collect(Collectors.toList()).get(0);
    }

    protected void loadMisfireInstruction(ScheduleBuilder<Trigger> scheduleBuilder, MisfireInstruction misfireInstruction) {
        switch (misfireInstruction) {
            case SIMPLE_MISFIRE_INSTRUCTION_FIRE_NOW:
                ((SimpleScheduleBuilder) scheduleBuilder).withMisfireHandlingInstructionFireNow();
                break;
            case SIMPLE_MISFIRE_INSTRUCTION_IGNORE_MISFIRE_POLICY:
                ((SimpleScheduleBuilder) scheduleBuilder).withMisfireHandlingInstructionIgnoreMisfires();
                break;
            case SIMPLE_MISFIRE_INSTRUCTION_RESCHEDULE_NEXT_WITH_EXISTING_COUNT:
                ((SimpleScheduleBuilder) scheduleBuilder).withMisfireHandlingInstructionNextWithExistingCount();
                break;
            case SIMPLE_MISFIRE_INSTRUCTION_RESCHEDULE_NEXT_WITH_REMAINING_COUNT:
                ((SimpleScheduleBuilder) scheduleBuilder).withMisfireHandlingInstructionNextWithRemainingCount();
                break;
            case SIMPLE_MISFIRE_INSTRUCTION_RESCHEDULE_NOW_WITH_EXISTING_REPEAT_COUNT:
                ((SimpleScheduleBuilder) scheduleBuilder).withMisfireHandlingInstructionNowWithExistingCount();
                break;
            case SIMPLE_MISFIRE_INSTRUCTION_RESCHEDULE_NOW_WITH_REMAINING_REPEAT_COUNT:
                ((SimpleScheduleBuilder) scheduleBuilder).withMisfireHandlingInstructionNowWithRemainingCount();
                break;
            case DAILY_TIME_INTERVAL_MISFIRE_INSTRUCTION_DO_NOTHING:
                ((DailyTimeIntervalScheduleBuilder) scheduleBuilder).withMisfireHandlingInstructionDoNothing();
                break;
            case DAILY_TIME_INTERVAL_MISFIRE_INSTRUCTION_FIRE_ONCE_NOW:
                ((DailyTimeIntervalScheduleBuilder) scheduleBuilder).withMisfireHandlingInstructionFireAndProceed();
                break;
            case DAILY_TIME_INTERVAL_MISFIRE_INSTRUCTION_IGNORE_MISFIRE_POLICY:
                ((DailyTimeIntervalScheduleBuilder) scheduleBuilder).withMisfireHandlingInstructionIgnoreMisfires();
                break;
            case CRON_MISFIRE_INSTRUCTION_DO_NOTHING:
                ((CronScheduleBuilder) scheduleBuilder).withMisfireHandlingInstructionDoNothing();
                break;
            case CRON_MISFIRE_INSTRUCTION_FIRE_ONCE_NOW:
                ((CronScheduleBuilder) scheduleBuilder).withMisfireHandlingInstructionFireAndProceed();
                break;
            case CRON_MISFIRE_INSTRUCTION_IGNORE_MISFIRE_POLICY:
                ((CronScheduleBuilder) scheduleBuilder).withMisfireHandlingInstructionIgnoreMisfires();
                break;
            case CALENDAR_INTERVAL_MISFIRE_INSTRUCTION_DO_NOTHING:
                ((CalendarIntervalScheduleBuilder) scheduleBuilder).withMisfireHandlingInstructionDoNothing();
                break;
            case CALENDAR_INTERVAL_MISFIRE_INSTRUCTION_FIRE_ONCE_NOW:
                ((CalendarIntervalScheduleBuilder) scheduleBuilder).withMisfireHandlingInstructionFireAndProceed();
                break;
            case CALENDAR_INTERVAL_MISFIRE_INSTRUCTION_IGNORE_MISFIRE_POLICY:
                ((CalendarIntervalScheduleBuilder) scheduleBuilder).withMisfireHandlingInstructionIgnoreMisfires();
                break;
            default:
                break;
        }
    }

    @Override
    public Trigger copySourceToTarget(Entity entity, Trigger trigger) {
        // todo
        return trigger;
    }

    @Override
    public Entity copyTargetToSource(Trigger trigger, Entity entity) {
        // todo
        entity.setName(trigger.getKey().getName());
        entity.setGroupName(trigger.getKey().getGroup());
        entity.setDescription(trigger.getDescription());
        entity.setStartTime(trigger.getStartTime());
        entity.setEndTime(trigger.getEndTime());
        entity.setPriority(trigger.getPriority());
        entity.setCalendarName(trigger.getCalendarName());
        entity.setJobDetail(loadJobDetailEntity(trigger.getJobKey()));
        return entity;
    }

    /**
     * 加载 Entity
     *
     * @param trigger
     * @return
     */
    protected abstract Entity loadEntity(Trigger trigger);

//    @Override
//    protected Trigger copyTargetToSource(Entity entity) {
//        return TriggerBuilder.newTrigger()
//                .withIdentity(entity.getName(), entity.getGroupName())
//                .withDescription(entity.getDescription())
//                .withPriority(entity.getPriority())
//                .withSchedule(loadScheduleBuilder(entity))
//                .modifiedByCalendar(entity.getCalendarName())
//                .startAt(entity.getStartTime())
//                .endAt(entity.getEndTime())
//                .forJob(entity.getJobDetail().getName(), entity.getJobDetail().getGroupName())
//                .usingJobData(new JobDataMap(entity.getJobDetail().getJobDataMap()))
//                .build();
//    }

    /**
     * 加载 Trigger
     *
     * @param entity
     * @return
     */
    protected abstract ScheduleBuilder<Trigger> loadScheduleBuilder(Entity entity);

}
