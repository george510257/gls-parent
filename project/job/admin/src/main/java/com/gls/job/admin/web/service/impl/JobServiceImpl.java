package com.gls.job.admin.web.service.impl;

import com.gls.job.admin.core.cron.CronExpression;
import com.gls.job.admin.core.route.ExecutorRouteStrategyEnum;
import com.gls.job.admin.core.scheduler.MisfireStrategyEnum;
import com.gls.job.admin.core.scheduler.ScheduleTypeEnum;
import com.gls.job.admin.core.thread.JobScheduleHelper;
import com.gls.job.admin.core.util.I18nUtil;
import com.gls.job.admin.web.entity.JobGroupEntity;
import com.gls.job.admin.web.entity.JobInfoEntity;
import com.gls.job.admin.web.entity.JobLogReportEntity;
import com.gls.job.admin.web.repository.*;
import com.gls.job.admin.web.service.JobService;
import com.gls.job.core.biz.model.ReturnT;
import com.gls.job.core.enums.ExecutorBlockStrategyEnum;
import com.gls.job.core.glue.GlueTypeEnum;
import com.gls.job.core.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.MessageFormat;
import java.util.*;

/**
 * core job action for gls-job
 *
 * @author xuxueli 2016-5-28 15:30:33
 */
@Service
public class JobServiceImpl implements JobService {
    private static Logger logger = LoggerFactory.getLogger(JobServiceImpl.class);
    @Resource
    public JobLogRepository jobLogRepository;
    @Resource
    private JobGroupRepository jobGroupRepository;
    @Resource
    private JobInfoRepository jobInfoRepository;
    @Resource
    private JobLogGlueRepository jobLogGlueRepository;
    @Resource
    private JobLogReportRepository jobLogReportRepository;

    @Override
    public Map<String, Object> pageList(int start, int length, int jobGroup, int triggerStatus, String jobDesc, String executorHandler, String author) {

        // page list
        List<JobInfoEntity> list = jobInfoRepository.pageList(start, length, jobGroup, triggerStatus, jobDesc, executorHandler, author);
        int list_count = jobInfoRepository.pageListCount(start, length, jobGroup, triggerStatus, jobDesc, executorHandler, author);

        // package result
        Map<String, Object> maps = new HashMap<String, Object>();
        maps.put("recordsTotal", list_count);        // 总记录数
        maps.put("recordsFiltered", list_count);    // 过滤后的总记录数
        maps.put("data", list);                    // 分页列表
        return maps;
    }

    @Override
    public ReturnT<String> add(JobInfoEntity jobInfoEntity) {

        // valid base
        JobGroupEntity group = jobGroupRepository.load(jobInfoEntity.getJobGroup());
        if (group == null) {
            return new ReturnT<String>(ReturnT.FAIL_CODE, (I18nUtil.getString("system_please_choose") + I18nUtil.getString("job_info_field_jobgroup")));
        }
        if (jobInfoEntity.getJobDesc() == null || jobInfoEntity.getJobDesc().trim().length() == 0) {
            return new ReturnT<String>(ReturnT.FAIL_CODE, (I18nUtil.getString("system_please_input") + I18nUtil.getString("job_info_field_jobdesc")));
        }
        if (jobInfoEntity.getAuthor() == null || jobInfoEntity.getAuthor().trim().length() == 0) {
            return new ReturnT<String>(ReturnT.FAIL_CODE, (I18nUtil.getString("system_please_input") + I18nUtil.getString("job_info_field_author")));
        }

        // valid trigger
        ScheduleTypeEnum scheduleTypeEnum = ScheduleTypeEnum.match(jobInfoEntity.getScheduleType(), null);
        if (scheduleTypeEnum == null) {
            return new ReturnT<String>(ReturnT.FAIL_CODE, (I18nUtil.getString("schedule_type") + I18nUtil.getString("system_un_valid")));
        }
        if (scheduleTypeEnum == ScheduleTypeEnum.CRON) {
            if (jobInfoEntity.getScheduleConf() == null || !CronExpression.isValidExpression(jobInfoEntity.getScheduleConf())) {
                return new ReturnT<String>(ReturnT.FAIL_CODE, "Cron" + I18nUtil.getString("system_un_valid"));
            }
        } else if (scheduleTypeEnum == ScheduleTypeEnum.FIX_RATE/* || scheduleTypeEnum == ScheduleTypeEnum.FIX_DELAY*/) {
            if (jobInfoEntity.getScheduleConf() == null) {
                return new ReturnT<String>(ReturnT.FAIL_CODE, (I18nUtil.getString("schedule_type")));
            }
            try {
                int fixSecond = Integer.valueOf(jobInfoEntity.getScheduleConf());
                if (fixSecond < 1) {
                    return new ReturnT<String>(ReturnT.FAIL_CODE, (I18nUtil.getString("schedule_type") + I18nUtil.getString("system_un_valid")));
                }
            } catch (Exception e) {
                return new ReturnT<String>(ReturnT.FAIL_CODE, (I18nUtil.getString("schedule_type") + I18nUtil.getString("system_un_valid")));
            }
        }

        // valid job
        if (GlueTypeEnum.match(jobInfoEntity.getGlueType()) == null) {
            return new ReturnT<String>(ReturnT.FAIL_CODE, (I18nUtil.getString("job_info_field_glue_type") + I18nUtil.getString("system_un_valid")));
        }
        if (GlueTypeEnum.BEAN == GlueTypeEnum.match(jobInfoEntity.getGlueType()) && (jobInfoEntity.getExecutorHandler() == null || jobInfoEntity.getExecutorHandler().trim().length() == 0)) {
            return new ReturnT<String>(ReturnT.FAIL_CODE, (I18nUtil.getString("system_please_input") + "JobHandler"));
        }
        // 》fix "\r" in shell
        if (GlueTypeEnum.GLUE_SHELL == GlueTypeEnum.match(jobInfoEntity.getGlueType()) && jobInfoEntity.getGlueSource() != null) {
            jobInfoEntity.setGlueSource(jobInfoEntity.getGlueSource().replaceAll("\r", ""));
        }

        // valid advanced
        if (ExecutorRouteStrategyEnum.match(jobInfoEntity.getExecutorRouteStrategy(), null) == null) {
            return new ReturnT<String>(ReturnT.FAIL_CODE, (I18nUtil.getString("job_info_field_executorRouteStrategy") + I18nUtil.getString("system_un_valid")));
        }
        if (MisfireStrategyEnum.match(jobInfoEntity.getMisfireStrategy(), null) == null) {
            return new ReturnT<String>(ReturnT.FAIL_CODE, (I18nUtil.getString("misfire_strategy") + I18nUtil.getString("system_un_valid")));
        }
        if (ExecutorBlockStrategyEnum.match(jobInfoEntity.getExecutorBlockStrategy(), null) == null) {
            return new ReturnT<String>(ReturnT.FAIL_CODE, (I18nUtil.getString("job_info_field_executorBlockStrategy") + I18nUtil.getString("system_un_valid")));
        }

        // 》ChildJobId valid
        if (jobInfoEntity.getChildJobId() != null && jobInfoEntity.getChildJobId().trim().length() > 0) {
            String[] childJobIds = jobInfoEntity.getChildJobId().split(",");
            for (String childJobIdItem : childJobIds) {
                if (childJobIdItem != null && childJobIdItem.trim().length() > 0 && isNumeric(childJobIdItem)) {
                    JobInfoEntity childJobInfoEntity = jobInfoRepository.loadById(Integer.parseInt(childJobIdItem));
                    if (childJobInfoEntity == null) {
                        return new ReturnT<String>(ReturnT.FAIL_CODE,
                                MessageFormat.format((I18nUtil.getString("job_info_field_childJobId") + "({0})" + I18nUtil.getString("system_not_found")), childJobIdItem));
                    }
                } else {
                    return new ReturnT<String>(ReturnT.FAIL_CODE,
                            MessageFormat.format((I18nUtil.getString("job_info_field_childJobId") + "({0})" + I18nUtil.getString("system_un_valid")), childJobIdItem));
                }
            }

            // join , avoid "xxx,,"
            String temp = "";
            for (String item : childJobIds) {
                temp += item + ",";
            }
            temp = temp.substring(0, temp.length() - 1);

            jobInfoEntity.setChildJobId(temp);
        }

        // add in db
        jobInfoEntity.setAddTime(new Date());
        jobInfoEntity.setUpdateTime(new Date());
        jobInfoEntity.setGlueUpdatetime(new Date());
        jobInfoRepository.save(jobInfoEntity);
        if (jobInfoEntity.getId() < 1) {
            return new ReturnT<String>(ReturnT.FAIL_CODE, (I18nUtil.getString("job_info_field_add") + I18nUtil.getString("system_fail")));
        }

        return new ReturnT<String>(String.valueOf(jobInfoEntity.getId()));
    }

    private boolean isNumeric(String str) {
        try {
            int result = Integer.valueOf(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public ReturnT<String> update(JobInfoEntity jobInfoEntity) {

        // valid base
        if (jobInfoEntity.getJobDesc() == null || jobInfoEntity.getJobDesc().trim().length() == 0) {
            return new ReturnT<String>(ReturnT.FAIL_CODE, (I18nUtil.getString("system_please_input") + I18nUtil.getString("job_info_field_jobdesc")));
        }
        if (jobInfoEntity.getAuthor() == null || jobInfoEntity.getAuthor().trim().length() == 0) {
            return new ReturnT<String>(ReturnT.FAIL_CODE, (I18nUtil.getString("system_please_input") + I18nUtil.getString("job_info_field_author")));
        }

        // valid trigger
        ScheduleTypeEnum scheduleTypeEnum = ScheduleTypeEnum.match(jobInfoEntity.getScheduleType(), null);
        if (scheduleTypeEnum == null) {
            return new ReturnT<String>(ReturnT.FAIL_CODE, (I18nUtil.getString("schedule_type") + I18nUtil.getString("system_un_valid")));
        }
        if (scheduleTypeEnum == ScheduleTypeEnum.CRON) {
            if (jobInfoEntity.getScheduleConf() == null || !CronExpression.isValidExpression(jobInfoEntity.getScheduleConf())) {
                return new ReturnT<String>(ReturnT.FAIL_CODE, "Cron" + I18nUtil.getString("system_un_valid"));
            }
        } else if (scheduleTypeEnum == ScheduleTypeEnum.FIX_RATE /*|| scheduleTypeEnum == ScheduleTypeEnum.FIX_DELAY*/) {
            if (jobInfoEntity.getScheduleConf() == null) {
                return new ReturnT<String>(ReturnT.FAIL_CODE, (I18nUtil.getString("schedule_type") + I18nUtil.getString("system_un_valid")));
            }
            try {
                int fixSecond = Integer.valueOf(jobInfoEntity.getScheduleConf());
                if (fixSecond < 1) {
                    return new ReturnT<String>(ReturnT.FAIL_CODE, (I18nUtil.getString("schedule_type") + I18nUtil.getString("system_un_valid")));
                }
            } catch (Exception e) {
                return new ReturnT<String>(ReturnT.FAIL_CODE, (I18nUtil.getString("schedule_type") + I18nUtil.getString("system_un_valid")));
            }
        }

        // valid advanced
        if (ExecutorRouteStrategyEnum.match(jobInfoEntity.getExecutorRouteStrategy(), null) == null) {
            return new ReturnT<String>(ReturnT.FAIL_CODE, (I18nUtil.getString("job_info_field_executorRouteStrategy") + I18nUtil.getString("system_un_valid")));
        }
        if (MisfireStrategyEnum.match(jobInfoEntity.getMisfireStrategy(), null) == null) {
            return new ReturnT<String>(ReturnT.FAIL_CODE, (I18nUtil.getString("misfire_strategy") + I18nUtil.getString("system_un_valid")));
        }
        if (ExecutorBlockStrategyEnum.match(jobInfoEntity.getExecutorBlockStrategy(), null) == null) {
            return new ReturnT<String>(ReturnT.FAIL_CODE, (I18nUtil.getString("job_info_field_executorBlockStrategy") + I18nUtil.getString("system_un_valid")));
        }

        // 》ChildJobId valid
        if (jobInfoEntity.getChildJobId() != null && jobInfoEntity.getChildJobId().trim().length() > 0) {
            String[] childJobIds = jobInfoEntity.getChildJobId().split(",");
            for (String childJobIdItem : childJobIds) {
                if (childJobIdItem != null && childJobIdItem.trim().length() > 0 && isNumeric(childJobIdItem)) {
                    JobInfoEntity childJobInfoEntity = jobInfoRepository.loadById(Integer.parseInt(childJobIdItem));
                    if (childJobInfoEntity == null) {
                        return new ReturnT<String>(ReturnT.FAIL_CODE,
                                MessageFormat.format((I18nUtil.getString("job_info_field_childJobId") + "({0})" + I18nUtil.getString("system_not_found")), childJobIdItem));
                    }
                } else {
                    return new ReturnT<String>(ReturnT.FAIL_CODE,
                            MessageFormat.format((I18nUtil.getString("job_info_field_childJobId") + "({0})" + I18nUtil.getString("system_un_valid")), childJobIdItem));
                }
            }

            // join , avoid "xxx,,"
            String temp = "";
            for (String item : childJobIds) {
                temp += item + ",";
            }
            temp = temp.substring(0, temp.length() - 1);

            jobInfoEntity.setChildJobId(temp);
        }

        // group valid
        JobGroupEntity jobGroupEntity = jobGroupRepository.load(jobInfoEntity.getJobGroup());
        if (jobGroupEntity == null) {
            return new ReturnT<String>(ReturnT.FAIL_CODE, (I18nUtil.getString("job_info_field_jobgroup") + I18nUtil.getString("system_un_valid")));
        }

        // stage job info
        JobInfoEntity existsJobInfoEntity = jobInfoRepository.loadById(jobInfoEntity.getId());
        if (existsJobInfoEntity == null) {
            return new ReturnT<String>(ReturnT.FAIL_CODE, (I18nUtil.getString("job_info_field_id") + I18nUtil.getString("system_not_found")));
        }

        // next trigger time (5s后生效，避开预读周期)
        long nextTriggerTime = existsJobInfoEntity.getTriggerNextTime();
        boolean scheduleDataNotChanged = jobInfoEntity.getScheduleType().equals(existsJobInfoEntity.getScheduleType()) && jobInfoEntity.getScheduleConf().equals(existsJobInfoEntity.getScheduleConf());
        if (existsJobInfoEntity.getTriggerStatus() == 1 && !scheduleDataNotChanged) {
            try {
                Date nextValidTime = JobScheduleHelper.generateNextValidTime(jobInfoEntity, new Date(System.currentTimeMillis() + JobScheduleHelper.PRE_READ_MS));
                if (nextValidTime == null) {
                    return new ReturnT<String>(ReturnT.FAIL_CODE, (I18nUtil.getString("schedule_type") + I18nUtil.getString("system_un_valid")));
                }
                nextTriggerTime = nextValidTime.getTime();
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                return new ReturnT<String>(ReturnT.FAIL_CODE, (I18nUtil.getString("schedule_type") + I18nUtil.getString("system_un_valid")));
            }
        }

        existsJobInfoEntity.setJobGroup(jobInfoEntity.getJobGroup());
        existsJobInfoEntity.setJobDesc(jobInfoEntity.getJobDesc());
        existsJobInfoEntity.setAuthor(jobInfoEntity.getAuthor());
        existsJobInfoEntity.setAlarmEmail(jobInfoEntity.getAlarmEmail());
        existsJobInfoEntity.setScheduleType(jobInfoEntity.getScheduleType());
        existsJobInfoEntity.setScheduleConf(jobInfoEntity.getScheduleConf());
        existsJobInfoEntity.setMisfireStrategy(jobInfoEntity.getMisfireStrategy());
        existsJobInfoEntity.setExecutorRouteStrategy(jobInfoEntity.getExecutorRouteStrategy());
        existsJobInfoEntity.setExecutorHandler(jobInfoEntity.getExecutorHandler());
        existsJobInfoEntity.setExecutorParam(jobInfoEntity.getExecutorParam());
        existsJobInfoEntity.setExecutorBlockStrategy(jobInfoEntity.getExecutorBlockStrategy());
        existsJobInfoEntity.setExecutorTimeout(jobInfoEntity.getExecutorTimeout());
        existsJobInfoEntity.setExecutorFailRetryCount(jobInfoEntity.getExecutorFailRetryCount());
        existsJobInfoEntity.setChildJobId(jobInfoEntity.getChildJobId());
        existsJobInfoEntity.setTriggerNextTime(nextTriggerTime);

        existsJobInfoEntity.setUpdateTime(new Date());
        jobInfoRepository.update(existsJobInfoEntity);

        return ReturnT.SUCCESS;
    }

    @Override
    public ReturnT<String> remove(int id) {
        JobInfoEntity jobInfoEntity = jobInfoRepository.loadById(id);
        if (jobInfoEntity == null) {
            return ReturnT.SUCCESS;
        }

        jobInfoRepository.delete(id);
        jobLogRepository.delete(id);
        jobLogGlueRepository.deleteByJobId(id);
        return ReturnT.SUCCESS;
    }

    @Override
    public ReturnT<String> start(int id) {
        JobInfoEntity jobInfoEntity = jobInfoRepository.loadById(id);

        // valid
        ScheduleTypeEnum scheduleTypeEnum = ScheduleTypeEnum.match(jobInfoEntity.getScheduleType(), ScheduleTypeEnum.NONE);
        if (ScheduleTypeEnum.NONE == scheduleTypeEnum) {
            return new ReturnT<String>(ReturnT.FAIL_CODE, (I18nUtil.getString("schedule_type_none_limit_start")));
        }

        // next trigger time (5s后生效，避开预读周期)
        long nextTriggerTime = 0;
        try {
            Date nextValidTime = JobScheduleHelper.generateNextValidTime(jobInfoEntity, new Date(System.currentTimeMillis() + JobScheduleHelper.PRE_READ_MS));
            if (nextValidTime == null) {
                return new ReturnT<String>(ReturnT.FAIL_CODE, (I18nUtil.getString("schedule_type") + I18nUtil.getString("system_un_valid")));
            }
            nextTriggerTime = nextValidTime.getTime();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new ReturnT<String>(ReturnT.FAIL_CODE, (I18nUtil.getString("schedule_type") + I18nUtil.getString("system_un_valid")));
        }

        jobInfoEntity.setTriggerStatus(1);
        jobInfoEntity.setTriggerLastTime(0);
        jobInfoEntity.setTriggerNextTime(nextTriggerTime);

        jobInfoEntity.setUpdateTime(new Date());
        jobInfoRepository.update(jobInfoEntity);
        return ReturnT.SUCCESS;
    }

    @Override
    public ReturnT<String> stop(int id) {
        JobInfoEntity jobInfoEntity = jobInfoRepository.loadById(id);

        jobInfoEntity.setTriggerStatus(0);
        jobInfoEntity.setTriggerLastTime(0);
        jobInfoEntity.setTriggerNextTime(0);

        jobInfoEntity.setUpdateTime(new Date());
        jobInfoRepository.update(jobInfoEntity);
        return ReturnT.SUCCESS;
    }

    @Override
    public Map<String, Object> dashboardInfo() {

        int jobInfoCount = jobInfoRepository.findAllCount();
        int jobLogCount = 0;
        int jobLogSuccessCount = 0;
        JobLogReportEntity jobLogReportEntity = jobLogReportRepository.queryLogReportTotal();
        if (jobLogReportEntity != null) {
            jobLogCount = jobLogReportEntity.getRunningCount() + jobLogReportEntity.getSucCount() + jobLogReportEntity.getFailCount();
            jobLogSuccessCount = jobLogReportEntity.getSucCount();
        }

        // executor count
        Set<String> executorAddressSet = new HashSet<String>();
        List<JobGroupEntity> groupList = jobGroupRepository.findAll();

        if (groupList != null && !groupList.isEmpty()) {
            for (JobGroupEntity group : groupList) {
                if (group.getRegistryList() != null && !group.getRegistryList().isEmpty()) {
                    executorAddressSet.addAll(group.getRegistryList());
                }
            }
        }

        int executorCount = executorAddressSet.size();

        Map<String, Object> dashboardMap = new HashMap<String, Object>();
        dashboardMap.put("jobInfoCount", jobInfoCount);
        dashboardMap.put("jobLogCount", jobLogCount);
        dashboardMap.put("jobLogSuccessCount", jobLogSuccessCount);
        dashboardMap.put("executorCount", executorCount);
        return dashboardMap;
    }

    @Override
    public ReturnT<Map<String, Object>> chartInfo(Date startDate, Date endDate) {

        // process
        List<String> triggerDayList = new ArrayList<String>();
        List<Integer> triggerDayCountRunningList = new ArrayList<Integer>();
        List<Integer> triggerDayCountSucList = new ArrayList<Integer>();
        List<Integer> triggerDayCountFailList = new ArrayList<Integer>();
        int triggerCountRunningTotal = 0;
        int triggerCountSucTotal = 0;
        int triggerCountFailTotal = 0;

        List<JobLogReportEntity> logReportList = jobLogReportRepository.queryLogReport(startDate, endDate);

        if (logReportList != null && logReportList.size() > 0) {
            for (JobLogReportEntity item : logReportList) {
                String day = DateUtil.formatDate(item.getTriggerDay());
                int triggerDayCountRunning = item.getRunningCount();
                int triggerDayCountSuc = item.getSucCount();
                int triggerDayCountFail = item.getFailCount();

                triggerDayList.add(day);
                triggerDayCountRunningList.add(triggerDayCountRunning);
                triggerDayCountSucList.add(triggerDayCountSuc);
                triggerDayCountFailList.add(triggerDayCountFail);

                triggerCountRunningTotal += triggerDayCountRunning;
                triggerCountSucTotal += triggerDayCountSuc;
                triggerCountFailTotal += triggerDayCountFail;
            }
        } else {
            for (int i = -6; i <= 0; i++) {
                triggerDayList.add(DateUtil.formatDate(DateUtil.addDays(new Date(), i)));
                triggerDayCountRunningList.add(0);
                triggerDayCountSucList.add(0);
                triggerDayCountFailList.add(0);
            }
        }

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("triggerDayList", triggerDayList);
        result.put("triggerDayCountRunningList", triggerDayCountRunningList);
        result.put("triggerDayCountSucList", triggerDayCountSucList);
        result.put("triggerDayCountFailList", triggerDayCountFailList);

        result.put("triggerCountRunningTotal", triggerCountRunningTotal);
        result.put("triggerCountSucTotal", triggerCountSucTotal);
        result.put("triggerCountFailTotal", triggerCountFailTotal);

        return new ReturnT<Map<String, Object>>(result);
    }

}
