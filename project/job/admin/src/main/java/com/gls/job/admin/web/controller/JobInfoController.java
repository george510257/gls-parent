package com.gls.job.admin.web.controller;

import com.gls.job.admin.core.enums.ExecutorRouteStrategy;
import com.gls.job.admin.core.enums.MisfireStrategy;
import com.gls.job.admin.core.enums.ScheduleType;
import com.gls.job.admin.core.enums.TriggerType;
import com.gls.job.admin.core.i18n.I18nHelper;
import com.gls.job.admin.web.dao.JobGroupDao;
import com.gls.job.admin.web.model.JobGroup;
import com.gls.job.admin.web.model.JobInfo;
import com.gls.job.admin.web.model.JobUser;
import com.gls.job.admin.web.service.JobInfoService;
import com.gls.job.admin.web.service.JobSchedulerService;
import com.gls.job.admin.web.service.JobTriggerService;
import com.gls.job.admin.web.service.LoginService;
import com.gls.job.core.api.model.Result;
import com.gls.job.core.api.model.enums.ExecutorBlockStrategy;
import com.gls.job.core.api.model.enums.GlueType;
import com.gls.job.core.exception.JobException;
import com.gls.job.core.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@Controller
@RequestMapping("/jobinfo")
public class JobInfoController {
    private static Logger logger = LoggerFactory.getLogger(JobInfoController.class);

    @Resource
    private JobGroupDao jobGroupDao;
    @Resource
    private JobInfoService jobInfoService;
    @Resource
    private JobSchedulerService jobSchedulerService;
    @Resource
    private JobTriggerService jobTriggerService;
    @Resource
    private I18nHelper i18nHelper;

    public List<JobGroup> filterJobGroupByRole(HttpServletRequest request, List<JobGroup> jobGroupList_all) {
        List<JobGroup> jobGroupList = new ArrayList<>();
        if (jobGroupList_all != null && jobGroupList_all.size() > 0) {
            JobUser loginUser = (JobUser) request.getAttribute(LoginService.LOGIN_IDENTITY_KEY);
            if (loginUser.getRole() == 1) {
                jobGroupList = jobGroupList_all;
            } else {
                List<String> groupIdStrs = new ArrayList<>();
                if (loginUser.getPermission() != null && loginUser.getPermission().trim().length() > 0) {
                    groupIdStrs = Arrays.asList(loginUser.getPermission().trim().split(","));
                }
                for (JobGroup groupItem : jobGroupList_all) {
                    if (groupIdStrs.contains(String.valueOf(groupItem.getId()))) {
                        jobGroupList.add(groupItem);
                    }
                }
            }
        }
        return jobGroupList;
    }

    public void validPermission(HttpServletRequest request, Long jobGroup) {
        JobUser loginUser = (JobUser) request.getAttribute(LoginService.LOGIN_IDENTITY_KEY);
        if (!loginUser.validPermission(jobGroup)) {
            throw new RuntimeException(i18nHelper.getString("system_permission_limit") + "[username=" + loginUser.getUsername() + "]");
        }
    }

    @RequestMapping
    public String index(HttpServletRequest request, Model model, @RequestParam(required = false, defaultValue = "-1") Long jobGroup) {

        // 枚举-字典
        model.addAttribute("ExecutorRouteStrategyEnum", ExecutorRouteStrategy.values());        // 路由策略-列表
        model.addAttribute("GlueTypeEnum", GlueType.values());                                // Glue类型-字典
        model.addAttribute("ExecutorBlockStrategyEnum", ExecutorBlockStrategy.values());        // 阻塞处理策略-字典
        model.addAttribute("ScheduleTypeEnum", ScheduleType.values());                        // 调度类型
        model.addAttribute("MisfireStrategyEnum", MisfireStrategy.values());                    // 调度过期策略

        // 执行器列表
        List<JobGroup> jobGroupList_all = jobGroupDao.findAll();

        // filter group
        List<JobGroup> jobGroupList = filterJobGroupByRole(request, jobGroupList_all);
        if (jobGroupList == null || jobGroupList.size() == 0) {
            throw new JobException(i18nHelper.getString("job_group_empty"));
        }

        model.addAttribute("JobGroupList", jobGroupList);
        model.addAttribute("jobGroup", jobGroup);

        return "jobinfo/jobinfo.index";
    }

    @RequestMapping("/pageList")
    @ResponseBody
    public Map<String, Object> pageList(@RequestParam(required = false, defaultValue = "0") int start,
                                        @RequestParam(required = false, defaultValue = "10") int length,
                                        Long jobGroup, int triggerStatus, String jobDesc, String executorHandler, String author) {

        return jobInfoService.pageList(start, length, jobGroup, triggerStatus, jobDesc, executorHandler, author);
    }

    @RequestMapping("/add")
    @ResponseBody
    public Result<String> add(JobInfo jobInfo) {
        return jobInfoService.add(jobInfo);
    }

    @RequestMapping("/update")
    @ResponseBody
    public Result<String> update(JobInfo jobInfo) {
        return jobInfoService.update(jobInfo);
    }

    @RequestMapping("/remove")
    @ResponseBody
    public Result<String> remove(Long id) {
        return jobInfoService.remove(id);
    }

    @RequestMapping("/stop")
    @ResponseBody
    public Result<String> pause(Long id) {
        return jobInfoService.stop(id);
    }

    @RequestMapping("/start")
    @ResponseBody
    public Result<String> start(Long id) {
        return jobInfoService.start(id);
    }

    @RequestMapping("/trigger")
    @ResponseBody
    //@PermissionLimit(limit = false)
    public Result<String> triggerJob(Long id, String executorParam, String addressList) {
        // force cover job param
        if (executorParam == null) {
            executorParam = "";
        }

        jobTriggerService.trigger(id, TriggerType.MANUAL, -1, null, executorParam, addressList);
        return Result.SUCCESS;
    }

    @RequestMapping("/nextTriggerTime")
    @ResponseBody
    public Result<List<String>> nextTriggerTime(ScheduleType scheduleType, String scheduleConf) {

        JobInfo paramJobInfo = new JobInfo();
        paramJobInfo.setScheduleType(scheduleType);
        paramJobInfo.setScheduleConf(scheduleConf);

        List<String> result = new ArrayList<>();
        try {
            Date lastTime = new Date();
            for (int i = 0; i < 5; i++) {
                lastTime = jobSchedulerService.generateNextValidTime(paramJobInfo, lastTime);
                if (lastTime != null) {
                    result.add(DateUtil.formatDateTime(lastTime));
                } else {
                    break;
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new Result<List<String>>(Result.FAIL_CODE, (i18nHelper.getString("schedule_type") + i18nHelper.getString("system_unvalid")) + e.getMessage());
        }
        return new Result<List<String>>(result);

    }

}
