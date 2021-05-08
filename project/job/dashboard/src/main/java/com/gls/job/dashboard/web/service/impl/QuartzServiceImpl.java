package com.gls.job.dashboard.web.service.impl;

import com.gls.job.dashboard.web.service.QuartzService;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.*;

/**
 * @author george
 */
@Service
public class QuartzServiceImpl implements QuartzService {

    @Resource
    private Scheduler scheduler;

    @PostConstruct
    public void startScheduler() throws SchedulerException {
        scheduler.start();
    }

    @Override
    public void addJob(Class<? extends QuartzJobBean> jobClass, String jobName, String jobGroupName, int jobTime, int jobTimes, Map<String, Object> jobData) throws SchedulerException {
        // 任务名称和组构成任务key
        JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(jobName, jobGroupName).build();
        // 设置job参数
        if (jobData != null && jobData.size() > 0) {
            jobDetail.getJobDataMap().putAll(jobData);
        }
        // 使用simpleTrigger规则
        if (jobTimes <= SimpleTrigger.REPEAT_INDEFINITELY) {
            jobTimes = SimpleTrigger.REPEAT_INDEFINITELY;
        }
        Trigger trigger = TriggerBuilder.newTrigger().withIdentity(jobName, jobGroupName)
                .withSchedule(SimpleScheduleBuilder.repeatSecondlyForever().withIntervalInSeconds(jobTime).withRepeatCount(jobTimes))
                .startNow().build();
        scheduler.scheduleJob(jobDetail, trigger);
    }

    @Override
    public void addJob(Class<? extends QuartzJobBean> jobClass, String jobName, String jobGroupName, String jobTime, Map<String, Object> jobData) throws SchedulerException {
        // 任务名称和组构成任务key
        JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(jobName, jobGroupName).build();
        // 设置job参数
        if (jobData != null && jobData.size() > 0) {
            jobDetail.getJobDataMap().putAll(jobData);
        }
        // 使用cornTrigger规则
        Trigger trigger = TriggerBuilder.newTrigger().withIdentity(jobName, jobGroupName)
                .withSchedule(CronScheduleBuilder.cronSchedule(jobTime))
                .startAt(DateBuilder.futureDate(1, DateBuilder.IntervalUnit.SECOND))
                .startNow().build();
        // 把作业和触发器注册到任务调度中
        scheduler.scheduleJob(jobDetail, trigger);
    }

    @Override
    public void updateJob(String jobName, String jobGroupName, String jobTime) throws SchedulerException {
        TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroupName);
        CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
        trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(CronScheduleBuilder.cronSchedule(jobTime)).build();
        // 重启触发器
        scheduler.rescheduleJob(triggerKey, trigger);

    }

    @Override
    public void deleteJob(String jobName, String jobGroupName) throws SchedulerException {
        JobKey jobKey = JobKey.jobKey(jobName, jobGroupName);
        scheduler.deleteJob(jobKey);
    }

    @Override
    public void pauseJob(String jobName, String jobGroupName) throws SchedulerException {
        JobKey jobKey = JobKey.jobKey(jobName, jobGroupName);
        scheduler.pauseJob(jobKey);
    }

    @Override
    public void resumeJob(String jobName, String jobGroupName) throws SchedulerException {
        JobKey jobKey = JobKey.jobKey(jobName, jobGroupName);
        scheduler.resumeJob(jobKey);
    }

    @Override
    public void runJobNow(String jobName, String jobGroupName) throws SchedulerException {
        JobKey jobKey = JobKey.jobKey(jobName, jobGroupName);
        scheduler.triggerJob(jobKey);
    }

    @Override
    public List<Map<String, Object>> queryAllJob() throws SchedulerException {
        GroupMatcher<JobKey> matcher = GroupMatcher.anyJobGroup();
        Set<JobKey> jobKeys = scheduler.getJobKeys(matcher);
        List<Map<String, Object>> jobList = new ArrayList<>();
        for (JobKey jobKey : jobKeys) {
            List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobKey);
            for (Trigger trigger : triggers) {
                jobList.add(getJobMap(jobKey, trigger));
            }
        }
        return jobList;
    }

    @Override
    public List<Map<String, Object>> queryRunJob() throws SchedulerException {
        List<JobExecutionContext> executingJobs = scheduler.getCurrentlyExecutingJobs();
        List<Map<String, Object>> jobList = new ArrayList<>(executingJobs.size());
        for (JobExecutionContext executingJob : executingJobs) {
            JobDetail jobDetail = executingJob.getJobDetail();
            JobKey jobKey = jobDetail.getKey();
            Trigger trigger = executingJob.getTrigger();
            jobList.add(getJobMap(jobKey, trigger));
        }
        return jobList;
    }

    private Map<String, Object> getJobMap(JobKey jobKey, Trigger trigger) throws SchedulerException {
        Map<String, Object> map = new HashMap<>();
        map.put("jobName", jobKey.getName());
        map.put("jobGroupName", jobKey.getGroup());
        map.put("description", "触发器:" + trigger.getKey());
        Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
        map.put("jobStatus", triggerState.name());
        if (trigger instanceof CronTrigger) {
            CronTrigger cronTrigger = (CronTrigger) trigger;
            String cronExpression = cronTrigger.getCronExpression();
            map.put("jobTime", cronExpression);
        }
        return map;
    }
}
