package com.gls.job.admin.web.controller;

import com.gls.job.admin.core.exception.JobException;
import com.gls.job.admin.core.route.ExecutorRouteStrategyEnum;
import com.gls.job.admin.core.scheduler.MisfireStrategyEnum;
import com.gls.job.admin.core.scheduler.ScheduleTypeEnum;
import com.gls.job.admin.core.thread.JobScheduleHelper;
import com.gls.job.admin.core.thread.JobTriggerPoolHelper;
import com.gls.job.admin.core.trigger.TriggerTypeEnum;
import com.gls.job.admin.core.util.I18nUtil;
import com.gls.job.admin.web.dao.JobGroupDao;
import com.gls.job.admin.web.model.JobGroup;
import com.gls.job.admin.web.model.JobInfo;
import com.gls.job.admin.web.model.JobUser;
import com.gls.job.admin.web.service.JobService;
import com.gls.job.admin.web.service.LoginService;
import com.gls.job.core.biz.model.ReturnT;
import com.gls.job.core.enums.ExecutorBlockStrategyEnum;
import com.gls.job.core.glue.GlueTypeEnum;
import com.gls.job.core.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * index controller
 *
 * @author xuxueli 2015-12-19 16:13:16
 */
@Slf4j
@Controller
@RequestMapping("/jobinfo")
public class JobInfoController {

    @Resource
    private JobGroupDao jobGroupDao;
    @Resource
    private JobService jobService;

    public static List<JobGroup> filterJobGroupByRole(HttpServletRequest request, List<JobGroup> jobGroupListAll) {
        List<JobGroup> jobGroupList = new ArrayList<>();
        if (jobGroupListAll != null && jobGroupListAll.size() > 0) {
            JobUser loginUser = (JobUser) request.getAttribute(LoginService.LOGIN_IDENTITY_KEY);
            if (loginUser.getRole() == 1) {
                jobGroupList = jobGroupListAll;
            } else {
                List<String> groupIds = new ArrayList<>();
                if (loginUser.getPermission() != null && loginUser.getPermission().trim().length() > 0) {
                    groupIds = Arrays.asList(loginUser.getPermission().trim().split(","));
                }
                for (JobGroup groupItem : jobGroupListAll) {
                    if (groupIds.contains(String.valueOf(groupItem.getId()))) {
                        jobGroupList.add(groupItem);
                    }
                }
            }
        }
        return jobGroupList;
    }

    public static void validPermission(HttpServletRequest request, int jobGroup) {
        JobUser loginUser = (JobUser) request.getAttribute(LoginService.LOGIN_IDENTITY_KEY);
        if (!loginUser.validPermission(jobGroup)) {
            throw new RuntimeException(I18nUtil.getString("system_permission_limit") + "[username=" + loginUser.getUsername() + "]");
        }
    }

    @RequestMapping
    public String index(HttpServletRequest request, Model model, @RequestParam(required = false, defaultValue = "-1") int jobGroup) {

        // 枚举-字典
        model.addAttribute("ExecutorRouteStrategyEnum", ExecutorRouteStrategyEnum.values());        // 路由策略-列表
        model.addAttribute("GlueTypeEnum", GlueTypeEnum.values());                                // Glue类型-字典
        model.addAttribute("ExecutorBlockStrategyEnum", ExecutorBlockStrategyEnum.values());        // 阻塞处理策略-字典
        model.addAttribute("ScheduleTypeEnum", ScheduleTypeEnum.values());                        // 调度类型
        model.addAttribute("MisfireStrategyEnum", MisfireStrategyEnum.values());                    // 调度过期策略

        // 执行器列表
        List<JobGroup> jobGroupListAll = jobGroupDao.findAll();

        // filter group
        List<JobGroup> jobGroupList = filterJobGroupByRole(request, jobGroupListAll);
        if (jobGroupList == null || jobGroupList.size() == 0) {
            throw new JobException(I18nUtil.getString("job_group_empty"));
        }

        model.addAttribute("JobGroupList", jobGroupList);
        model.addAttribute("jobGroup", jobGroup);

        return "jobinfo/jobinfo.index";
    }

    @RequestMapping("/pageList")
    @ResponseBody
    public Map<String, Object> pageList(@RequestParam(required = false, defaultValue = "0") int start,
                                        @RequestParam(required = false, defaultValue = "10") int length,
                                        int jobGroup, int triggerStatus, String jobDesc, String executorHandler, String author) {

        return jobService.pageList(start, length, jobGroup, triggerStatus, jobDesc, executorHandler, author);
    }

    @RequestMapping("/add")
    @ResponseBody
    public ReturnT<String> add(JobInfo jobInfo) {
        return jobService.add(jobInfo);
    }

    @RequestMapping("/update")
    @ResponseBody
    public ReturnT<String> update(JobInfo jobInfo) {
        return jobService.update(jobInfo);
    }

    @RequestMapping("/remove")
    @ResponseBody
    public ReturnT<String> remove(int id) {
        return jobService.remove(id);
    }

    @RequestMapping("/stop")
    @ResponseBody
    public ReturnT<String> pause(int id) {
        return jobService.stop(id);
    }

    @RequestMapping("/start")
    @ResponseBody
    public ReturnT<String> start(int id) {
        return jobService.start(id);
    }

    @RequestMapping("/trigger")
    @ResponseBody
    public ReturnT<String> triggerJob(int id, String executorParam, String addressList) {
        // force cover job param
        if (executorParam == null) {
            executorParam = "";
        }

        JobTriggerPoolHelper.trigger(id, TriggerTypeEnum.MANUAL, -1, null, executorParam, addressList);
        return ReturnT.SUCCESS;
    }

    @RequestMapping("/nextTriggerTime")
    @ResponseBody
    public ReturnT<List<String>> nextTriggerTime(String scheduleType, String scheduleConf) {

        JobInfo paramJobInfo = new JobInfo();
        paramJobInfo.setScheduleType(scheduleType);
        paramJobInfo.setScheduleConf(scheduleConf);

        List<String> result = new ArrayList<>();
        try {
            Date lastTime = new Date();
            for (int i = 0; i < 5; i++) {
                lastTime = JobScheduleHelper.generateNextValidTime(paramJobInfo, lastTime);
                if (lastTime != null) {
                    result.add(DateUtil.formatDateTime(lastTime));
                } else {
                    break;
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return new ReturnT<>(ReturnT.FAIL_CODE, (I18nUtil.getString("schedule_type") + I18nUtil.getString("system_un_valid")) + e.getMessage());
        }
        return new ReturnT<>(result);

    }

}
