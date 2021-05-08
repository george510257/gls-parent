package com.gls.job.admin.web.service.impl;

import com.gls.job.admin.core.cron.CronExpression;
import com.gls.job.admin.core.server.JobScheduleServer;
import com.gls.job.admin.core.util.I18nUtil;
import com.gls.job.admin.web.dao.*;
import com.gls.job.admin.web.entity.JobGroup;
import com.gls.job.admin.web.entity.JobInfo;
import com.gls.job.admin.web.entity.JobLogReport;
import com.gls.job.admin.web.entity.enums.ScheduleType;
import com.gls.job.admin.web.service.JobService;
import com.gls.job.core.api.model.Result;
import com.gls.job.core.api.model.enums.GlueType;
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
 * @author george 2016-5-28 15:30:33
 */
@Service
public class JobServiceImpl implements JobService {
    private static Logger logger = LoggerFactory.getLogger(JobServiceImpl.class);
    @Resource
    public JobLogDao jobLogDao;
    @Resource
    private JobGroupDao jobGroupDao;
    @Resource
    private JobInfoDao jobInfoDao;
    @Resource
    private JobLogGlueDao jobLogGlueDao;
    @Resource
    private JobLogReportDao jobLogReportDao;

    @Override
    public Map<String, Object> pageList(int start, int length, Long jobGroup, int triggerStatus, String jobDesc, String executorHandler, String author) {

        // page list
        List<JobInfo> list = jobInfoDao.pageList(start, length, jobGroup, triggerStatus, jobDesc, executorHandler, author);
        int list_count = jobInfoDao.pageListCount(start, length, jobGroup, triggerStatus, jobDesc, executorHandler, author);

        // package result
        Map<String, Object> maps = new HashMap<String, Object>();
        maps.put("recordsTotal", list_count);        // 总记录数
        maps.put("recordsFiltered", list_count);    // 过滤后的总记录数
        maps.put("data", list);                    // 分页列表
        return maps;
    }

    @Override
    public Result<String> add(JobInfo jobInfo) {

        // valid base
        JobGroup group = jobGroupDao.load(jobInfo.getJobGroup());
        if (group == null) {
            return new Result<String>(Result.FAIL_CODE, (I18nUtil.getString("system_please_choose") + I18nUtil.getString("job_info_field_job_group")));
        }
        if (jobInfo.getJobDesc() == null || jobInfo.getJobDesc().trim().length() == 0) {
            return new Result<String>(Result.FAIL_CODE, (I18nUtil.getString("system_please_input") + I18nUtil.getString("job_info_field_job_desc")));
        }
        if (jobInfo.getAuthor() == null || jobInfo.getAuthor().trim().length() == 0) {
            return new Result<String>(Result.FAIL_CODE, (I18nUtil.getString("system_please_input") + I18nUtil.getString("job_info_field_author")));
        }

        // valid trigger
        ScheduleType scheduleTypeEnum = jobInfo.getScheduleType();
        if (scheduleTypeEnum == null) {
            return new Result<String>(Result.FAIL_CODE, (I18nUtil.getString("schedule_type") + I18nUtil.getString("system_un_valid")));
        }
        if (scheduleTypeEnum == ScheduleType.CRON) {
            if (jobInfo.getScheduleConf() == null || !CronExpression.isValidExpression(jobInfo.getScheduleConf())) {
                return new Result<String>(Result.FAIL_CODE, "Cron" + I18nUtil.getString("system_un_valid"));
            }
        } else if (scheduleTypeEnum == ScheduleType.FIX_RATE/* || scheduleTypeEnum == ScheduleTypeEnum.FIX_DELAY*/) {
            if (jobInfo.getScheduleConf() == null) {
                return new Result<String>(Result.FAIL_CODE, (I18nUtil.getString("schedule_type")));
            }
            try {
                int fixSecond = Integer.valueOf(jobInfo.getScheduleConf());
                if (fixSecond < 1) {
                    return new Result<String>(Result.FAIL_CODE, (I18nUtil.getString("schedule_type") + I18nUtil.getString("system_un_valid")));
                }
            } catch (Exception e) {
                return new Result<String>(Result.FAIL_CODE, (I18nUtil.getString("schedule_type") + I18nUtil.getString("system_un_valid")));
            }
        }

        // valid job
        if (jobInfo.getGlueType() == null) {
            return new Result<String>(Result.FAIL_CODE, (I18nUtil.getString("job_info_field_glue_type") + I18nUtil.getString("system_un_valid")));
        }
        if (GlueType.BEAN == jobInfo.getGlueType() && (jobInfo.getExecutorHandler() == null || jobInfo.getExecutorHandler().trim().length() == 0)) {
            return new Result<String>(Result.FAIL_CODE, (I18nUtil.getString("system_please_input") + "JobHandler"));
        }
        // 》fix "\r" in shell
        if (GlueType.GLUE_SHELL == jobInfo.getGlueType() && jobInfo.getGlueSource() != null) {
            jobInfo.setGlueSource(jobInfo.getGlueSource().replaceAll("\r", ""));
        }

        // valid advanced
        if (jobInfo.getExecutorRouteStrategy() == null) {
            return new Result<String>(Result.FAIL_CODE, (I18nUtil.getString("job_info_field_executorRouteStrategy") + I18nUtil.getString("system_un_valid")));
        }
        if (jobInfo.getMisfireStrategy() == null) {
            return new Result<String>(Result.FAIL_CODE, (I18nUtil.getString("misfire_strategy") + I18nUtil.getString("system_un_valid")));
        }
        if (jobInfo.getExecutorBlockStrategy() == null) {
            return new Result<String>(Result.FAIL_CODE, (I18nUtil.getString("job_info_field_executorBlockStrategy") + I18nUtil.getString("system_un_valid")));
        }

        // 》ChildJobId valid
        if (jobInfo.getChildJobId() != null && jobInfo.getChildJobId().trim().length() > 0) {
            String[] childJobIds = jobInfo.getChildJobId().split(",");
            for (String childJobIdItem : childJobIds) {
                if (childJobIdItem != null && childJobIdItem.trim().length() > 0 && isNumeric(childJobIdItem)) {
                    JobInfo childJobInfo = jobInfoDao.loadById(Long.parseLong(childJobIdItem));
                    if (childJobInfo == null) {
                        return new Result<>(Result.FAIL_CODE,
                                MessageFormat.format((I18nUtil.getString("job_info_field_childJobId") + "({0})" + I18nUtil.getString("system_not_found")), childJobIdItem));
                    }
                } else {
                    return new Result<>(Result.FAIL_CODE,
                            MessageFormat.format((I18nUtil.getString("job_info_field_childJobId") + "({0})" + I18nUtil.getString("system_un_valid")), childJobIdItem));
                }
            }

            // join , avoid "xxx,,"
            String temp = "";
            for (String item : childJobIds) {
                temp += item + ",";
            }
            temp = temp.substring(0, temp.length() - 1);

            jobInfo.setChildJobId(temp);
        }

        // add in db
        jobInfo.setAddTime(new Date());
        jobInfo.setUpdateTime(new Date());
        jobInfo.setGlueUpdateTime(new Date());
        jobInfoDao.save(jobInfo);
        if (jobInfo.getId() < 1) {
            return new Result<String>(Result.FAIL_CODE, (I18nUtil.getString("job_info_field_add") + I18nUtil.getString("system_fail")));
        }

        return new Result<String>(String.valueOf(jobInfo.getId()));
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
    public Result<String> update(JobInfo jobInfo) {

        // valid base
        if (jobInfo.getJobDesc() == null || jobInfo.getJobDesc().trim().length() == 0) {
            return new Result<String>(Result.FAIL_CODE, (I18nUtil.getString("system_please_input") + I18nUtil.getString("job_info_field_job_desc")));
        }
        if (jobInfo.getAuthor() == null || jobInfo.getAuthor().trim().length() == 0) {
            return new Result<String>(Result.FAIL_CODE, (I18nUtil.getString("system_please_input") + I18nUtil.getString("job_info_field_author")));
        }

        // valid trigger
        ScheduleType scheduleTypeEnum = jobInfo.getScheduleType();
        if (scheduleTypeEnum == null) {
            return new Result<String>(Result.FAIL_CODE, (I18nUtil.getString("schedule_type") + I18nUtil.getString("system_un_valid")));
        }
        if (scheduleTypeEnum == ScheduleType.CRON) {
            if (jobInfo.getScheduleConf() == null || !CronExpression.isValidExpression(jobInfo.getScheduleConf())) {
                return new Result<String>(Result.FAIL_CODE, "Cron" + I18nUtil.getString("system_un_valid"));
            }
        } else if (scheduleTypeEnum == ScheduleType.FIX_RATE /*|| scheduleTypeEnum == ScheduleTypeEnum.FIX_DELAY*/) {
            if (jobInfo.getScheduleConf() == null) {
                return new Result<String>(Result.FAIL_CODE, (I18nUtil.getString("schedule_type") + I18nUtil.getString("system_un_valid")));
            }
            try {
                int fixSecond = Integer.valueOf(jobInfo.getScheduleConf());
                if (fixSecond < 1) {
                    return new Result<String>(Result.FAIL_CODE, (I18nUtil.getString("schedule_type") + I18nUtil.getString("system_un_valid")));
                }
            } catch (Exception e) {
                return new Result<String>(Result.FAIL_CODE, (I18nUtil.getString("schedule_type") + I18nUtil.getString("system_un_valid")));
            }
        }

        // valid advanced
        if (jobInfo.getExecutorRouteStrategy() == null) {
            return new Result<String>(Result.FAIL_CODE, (I18nUtil.getString("job_info_field_executorRouteStrategy") + I18nUtil.getString("system_un_valid")));
        }
        if (jobInfo.getMisfireStrategy() == null) {
            return new Result<String>(Result.FAIL_CODE, (I18nUtil.getString("misfire_strategy") + I18nUtil.getString("system_un_valid")));
        }
        if (jobInfo.getExecutorBlockStrategy() == null) {
            return new Result<String>(Result.FAIL_CODE, (I18nUtil.getString("job_info_field_executorBlockStrategy") + I18nUtil.getString("system_un_valid")));
        }

        // 》ChildJobId valid
        if (jobInfo.getChildJobId() != null && jobInfo.getChildJobId().trim().length() > 0) {
            String[] childJobIds = jobInfo.getChildJobId().split(",");
            for (String childJobIdItem : childJobIds) {
                if (childJobIdItem != null && childJobIdItem.trim().length() > 0 && isNumeric(childJobIdItem)) {
                    JobInfo childJobInfo = jobInfoDao.loadById(Long.parseLong(childJobIdItem));
                    if (childJobInfo == null) {
                        return new Result<>(Result.FAIL_CODE,
                                MessageFormat.format((I18nUtil.getString("job_info_field_childJobId") + "({0})" + I18nUtil.getString("system_not_found")), childJobIdItem));
                    }
                } else {
                    return new Result<>(Result.FAIL_CODE,
                            MessageFormat.format((I18nUtil.getString("job_info_field_childJobId") + "({0})" + I18nUtil.getString("system_un_valid")), childJobIdItem));
                }
            }

            // join , avoid "xxx,,"
            String temp = "";
            for (String item : childJobIds) {
                temp += item + ",";
            }
            temp = temp.substring(0, temp.length() - 1);

            jobInfo.setChildJobId(temp);
        }

        // group valid
        JobGroup jobGroup = jobGroupDao.load(jobInfo.getJobGroup());
        if (jobGroup == null) {
            return new Result<String>(Result.FAIL_CODE, (I18nUtil.getString("job_info_field_job_group") + I18nUtil.getString("system_un_valid")));
        }

        // stage job info
        JobInfo exists_jobInfo = jobInfoDao.loadById(jobInfo.getId());
        if (exists_jobInfo == null) {
            return new Result<String>(Result.FAIL_CODE, (I18nUtil.getString("job_info_field_id") + I18nUtil.getString("system_not_found")));
        }

        // next trigger time (5s后生效，避开预读周期)
        long nextTriggerTime = exists_jobInfo.getTriggerNextTime();
        boolean scheduleDataNotChanged = jobInfo.getScheduleType().equals(exists_jobInfo.getScheduleType()) && jobInfo.getScheduleConf().equals(exists_jobInfo.getScheduleConf());
        if (exists_jobInfo.getTriggerStatus() == 1 && !scheduleDataNotChanged) {
            try {
                Date nextValidTime = JobScheduleServer.generateNextValidTime(jobInfo, new Date(System.currentTimeMillis() + JobScheduleServer.PRE_READ_MS));
                if (nextValidTime == null) {
                    return new Result<String>(Result.FAIL_CODE, (I18nUtil.getString("schedule_type") + I18nUtil.getString("system_un_valid")));
                }
                nextTriggerTime = nextValidTime.getTime();
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                return new Result<String>(Result.FAIL_CODE, (I18nUtil.getString("schedule_type") + I18nUtil.getString("system_un_valid")));
            }
        }

        exists_jobInfo.setJobGroup(jobInfo.getJobGroup());
        exists_jobInfo.setJobDesc(jobInfo.getJobDesc());
        exists_jobInfo.setAuthor(jobInfo.getAuthor());
        exists_jobInfo.setAlarmEmail(jobInfo.getAlarmEmail());
        exists_jobInfo.setScheduleType(jobInfo.getScheduleType());
        exists_jobInfo.setScheduleConf(jobInfo.getScheduleConf());
        exists_jobInfo.setMisfireStrategy(jobInfo.getMisfireStrategy());
        exists_jobInfo.setExecutorRouteStrategy(jobInfo.getExecutorRouteStrategy());
        exists_jobInfo.setExecutorHandler(jobInfo.getExecutorHandler());
        exists_jobInfo.setExecutorParam(jobInfo.getExecutorParam());
        exists_jobInfo.setExecutorBlockStrategy(jobInfo.getExecutorBlockStrategy());
        exists_jobInfo.setExecutorTimeout(jobInfo.getExecutorTimeout());
        exists_jobInfo.setExecutorFailRetryCount(jobInfo.getExecutorFailRetryCount());
        exists_jobInfo.setChildJobId(jobInfo.getChildJobId());
        exists_jobInfo.setTriggerNextTime(nextTriggerTime);

        exists_jobInfo.setUpdateTime(new Date());
        jobInfoDao.update(exists_jobInfo);

        return Result.SUCCESS;
    }

    @Override
    public Result<String> remove(Long id) {
        JobInfo jobInfo = jobInfoDao.loadById(id);
        if (jobInfo == null) {
            return Result.SUCCESS;
        }

        jobInfoDao.delete(id);
        jobLogDao.delete(id);
        jobLogGlueDao.deleteByJobId(id);
        return Result.SUCCESS;
    }

    @Override
    public Result<String> start(Long id) {
        JobInfo jobInfo = jobInfoDao.loadById(id);

        // valid
        ScheduleType scheduleTypeEnum = jobInfo.getScheduleType();
        if (ScheduleType.NONE == scheduleTypeEnum) {
            return new Result<String>(Result.FAIL_CODE, (I18nUtil.getString("schedule_type_none_limit_start")));
        }

        // next trigger time (5s后生效，避开预读周期)
        long nextTriggerTime = 0;
        try {
            Date nextValidTime = JobScheduleServer.generateNextValidTime(jobInfo, new Date(System.currentTimeMillis() + JobScheduleServer.PRE_READ_MS));
            if (nextValidTime == null) {
                return new Result<String>(Result.FAIL_CODE, (I18nUtil.getString("schedule_type") + I18nUtil.getString("system_un_valid")));
            }
            nextTriggerTime = nextValidTime.getTime();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new Result<String>(Result.FAIL_CODE, (I18nUtil.getString("schedule_type") + I18nUtil.getString("system_un_valid")));
        }

        jobInfo.setTriggerStatus(1);
        jobInfo.setTriggerLastTime(0);
        jobInfo.setTriggerNextTime(nextTriggerTime);

        jobInfo.setUpdateTime(new Date());
        jobInfoDao.update(jobInfo);
        return Result.SUCCESS;
    }

    @Override
    public Result<String> stop(Long id) {
        JobInfo jobInfo = jobInfoDao.loadById(id);

        jobInfo.setTriggerStatus(0);
        jobInfo.setTriggerLastTime(0);
        jobInfo.setTriggerNextTime(0);

        jobInfo.setUpdateTime(new Date());
        jobInfoDao.update(jobInfo);
        return Result.SUCCESS;
    }

    @Override
    public Map<String, Object> dashboardInfo() {

        int jobInfoCount = jobInfoDao.findAllCount();
        int jobLogCount = 0;
        int jobLogSuccessCount = 0;
        JobLogReport jobLogReport = jobLogReportDao.queryLogReportTotal();
        if (jobLogReport != null) {
            jobLogCount = jobLogReport.getRunningCount() + jobLogReport.getSucCount() + jobLogReport.getFailCount();
            jobLogSuccessCount = jobLogReport.getSucCount();
        }

        // executor count
        Set<String> executorAddressSet = new HashSet<String>();
        List<JobGroup> groupList = jobGroupDao.findAll();

        if (groupList != null && !groupList.isEmpty()) {
            for (JobGroup group : groupList) {
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
    public Result<Map<String, Object>> chartInfo(Date startDate, Date endDate) {

        // process
        List<String> triggerDayList = new ArrayList<String>();
        List<Integer> triggerDayCountRunningList = new ArrayList<Integer>();
        List<Integer> triggerDayCountSucList = new ArrayList<Integer>();
        List<Integer> triggerDayCountFailList = new ArrayList<Integer>();
        int triggerCountRunningTotal = 0;
        int triggerCountSucTotal = 0;
        int triggerCountFailTotal = 0;

        List<JobLogReport> logReportList = jobLogReportDao.queryLogReport(startDate, endDate);

        if (logReportList != null && logReportList.size() > 0) {
            for (JobLogReport item : logReportList) {
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

        return new Result<Map<String, Object>>(result);
    }

}
