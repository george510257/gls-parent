package com.gls.job.dashboard.web.service;

import org.quartz.SchedulerException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.List;
import java.util.Map;

/**
 * @author george
 */
public interface QuartzService {
    /**
     * 增加一个job
     *
     * @param jobClass     任务实现类
     * @param jobName      任务名称
     * @param jobGroupName 任务组名
     * @param jobTime      时间表达式 (这是每隔多少秒为一次任务)
     * @param jobTimes     运行的次数 （<0:表示不限次数）
     * @param jobData      参数
     * @throws SchedulerException 定时任务异常
     */
    void addJob(Class<? extends QuartzJobBean> jobClass, String jobName, String jobGroupName, int jobTime, int jobTimes, Map<String, Object> jobData) throws SchedulerException;

    /**
     * 增加一个job
     *
     * @param jobClass     任务实现类
     * @param jobName      任务名称(建议唯一)
     * @param jobGroupName 任务组名
     * @param jobTime      时间表达式 （如：0/5 * * * * ? ）
     * @param jobData      参数
     * @throws SchedulerException 定时任务异常
     */
    void addJob(Class<? extends QuartzJobBean> jobClass, String jobName, String jobGroupName, String jobTime, Map<String, Object> jobData) throws SchedulerException;

    /**
     * 删除任务一个job
     *
     * @param jobName      任务名称
     * @param jobGroupName 任务组名
     * @throws SchedulerException 定时任务异常
     */
    void deleteJob(String jobName, String jobGroupName) throws SchedulerException;

    /**
     * 暂停一个job
     *
     * @param jobName      任务名称
     * @param jobGroupName 任务组名
     * @throws SchedulerException 定时任务异常
     */
    void pauseJob(String jobName, String jobGroupName) throws SchedulerException;

    /**
     * 获取所有计划中的任务列表
     *
     * @return 计划中的任务列表
     * @throws SchedulerException 定时任务异常
     */
    List<Map<String, Object>> queryAllJob() throws SchedulerException;

    /**
     * 获取所有正在运行的job
     *
     * @return 正在运行的job列表
     * @throws SchedulerException 定时任务异常
     */
    List<Map<String, Object>> queryRunJob() throws SchedulerException;

    /**
     * 恢复一个job
     *
     * @param jobName      任务名称
     * @param jobGroupName 任务组名
     * @throws SchedulerException 定时任务异常
     */
    void resumeJob(String jobName, String jobGroupName) throws SchedulerException;

    /**
     * 立即执行一个job
     *
     * @param jobName      任务名称
     * @param jobGroupName 任务组名
     * @throws SchedulerException 定时任务异常
     */
    void runJobNow(String jobName, String jobGroupName) throws SchedulerException;

    /**
     * 修改 一个job的 时间表达式
     *
     * @param jobName      任务名称(建议唯一)
     * @param jobGroupName 任务组名
     * @param jobTime      时间表达式 （如：0/5 * * * * ? ）
     * @throws SchedulerException 定时任务异常
     */
    void updateJob(String jobName, String jobGroupName, String jobTime) throws SchedulerException;
}
