package com.gls.job.admin.web.controller;

import com.gls.job.admin.core.exception.XxlJobException;
import com.gls.job.admin.core.server.JobScheduleHelper;
import com.gls.job.admin.core.server.JobTriggerPoolHelper;
import com.gls.job.admin.core.util.I18nUtil;
import com.gls.job.admin.web.dao.XxlJobGroupDao;
import com.gls.job.admin.web.entity.XxlJobGroup;
import com.gls.job.admin.web.entity.XxlJobInfo;
import com.gls.job.admin.web.entity.XxlJobUser;
import com.gls.job.admin.web.entity.enums.ExecutorRouteStrategy;
import com.gls.job.admin.web.entity.enums.MisfireStrategy;
import com.gls.job.admin.web.entity.enums.ScheduleType;
import com.gls.job.admin.web.entity.enums.TriggerType;
import com.gls.job.admin.web.service.LoginService;
import com.gls.job.admin.web.service.XxlJobService;
import com.gls.job.core.api.model.Result;
import com.gls.job.core.api.model.enums.ExecutorBlockStrategy;
import com.gls.job.core.api.model.enums.GlueType;
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
 * @author george 2015-12-19 16:13:16
 */
@Controller
@RequestMapping("/jobinfo")
public class JobInfoController {
    private static Logger logger = LoggerFactory.getLogger(JobInfoController.class);

    @Resource
    private XxlJobGroupDao glsJobGroupDao;
    @Resource
    private XxlJobService glsJobService;

    public static List<XxlJobGroup> filterJobGroupByRole(HttpServletRequest request, List<XxlJobGroup> jobGroupList_all) {
        List<XxlJobGroup> jobGroupList = new ArrayList<>();
        if (jobGroupList_all != null && jobGroupList_all.size() > 0) {
            XxlJobUser loginUser = (XxlJobUser) request.getAttribute(LoginService.LOGIN_IDENTITY_KEY);
            if (loginUser.getRole() == 1) {
                jobGroupList = jobGroupList_all;
            } else {
                List<String> groupIdStrs = new ArrayList<>();
                if (loginUser.getPermission() != null && loginUser.getPermission().trim().length() > 0) {
                    groupIdStrs = Arrays.asList(loginUser.getPermission().trim().split(","));
                }
                for (XxlJobGroup groupItem : jobGroupList_all) {
                    if (groupIdStrs.contains(String.valueOf(groupItem.getId()))) {
                        jobGroupList.add(groupItem);
                    }
                }
            }
        }
        return jobGroupList;
    }

    public static void validPermission(HttpServletRequest request, int jobGroup) {
        XxlJobUser loginUser = (XxlJobUser) request.getAttribute(LoginService.LOGIN_IDENTITY_KEY);
        if (!loginUser.validPermission(jobGroup)) {
            throw new RuntimeException(I18nUtil.getString("system_permission_limit") + "[username=" + loginUser.getUsername() + "]");
        }
    }

    @RequestMapping
    public String index(HttpServletRequest request, Model model, @RequestParam(required = false, defaultValue = "-1") int jobGroup) {

        // 枚举-字典
        model.addAttribute("ExecutorRouteStrategyEnum", ExecutorRouteStrategy.values());        // 路由策略-列表
        model.addAttribute("GlueTypeEnum", GlueType.values());                                // Glue类型-字典
        model.addAttribute("ExecutorBlockStrategyEnum", ExecutorBlockStrategy.values());        // 阻塞处理策略-字典
        model.addAttribute("ScheduleTypeEnum", ScheduleType.values());                        // 调度类型
        model.addAttribute("MisfireStrategyEnum", MisfireStrategy.values());                    // 调度过期策略

        // 执行器列表
        List<XxlJobGroup> jobGroupList_all = glsJobGroupDao.findAll();

        // filter group
        List<XxlJobGroup> jobGroupList = filterJobGroupByRole(request, jobGroupList_all);
        if (jobGroupList == null || jobGroupList.size() == 0) {
            throw new XxlJobException(I18nUtil.getString("jobgroup_empty"));
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

        return glsJobService.pageList(start, length, jobGroup, triggerStatus, jobDesc, executorHandler, author);
    }

    @RequestMapping("/add")
    @ResponseBody
    public Result<String> add(XxlJobInfo jobInfo) {
        return glsJobService.add(jobInfo);
    }

    @RequestMapping("/update")
    @ResponseBody
    public Result<String> update(XxlJobInfo jobInfo) {
        return glsJobService.update(jobInfo);
    }

    @RequestMapping("/remove")
    @ResponseBody
    public Result<String> remove(int id) {
        return glsJobService.remove(id);
    }

    @RequestMapping("/stop")
    @ResponseBody
    public Result<String> pause(int id) {
        return glsJobService.stop(id);
    }

    @RequestMapping("/start")
    @ResponseBody
    public Result<String> start(int id) {
        return glsJobService.start(id);
    }

    @RequestMapping("/trigger")
    @ResponseBody
    //@PermissionLimit(limit = false)
    public Result<String> triggerJob(int id, String executorParam, String addressList) {
        // force cover job param
        if (executorParam == null) {
            executorParam = "";
        }

        JobTriggerPoolHelper.trigger(id, TriggerType.MANUAL, -1, null, executorParam, addressList);
        return Result.SUCCESS;
    }

    @RequestMapping("/nextTriggerTime")
    @ResponseBody
    public Result<List<String>> nextTriggerTime(String scheduleType, String scheduleConf) {

        XxlJobInfo paramXxlJobInfo = new XxlJobInfo();
        paramXxlJobInfo.setScheduleType(ScheduleType.valueOf(scheduleType));
        paramXxlJobInfo.setScheduleConf(scheduleConf);

        List<String> result = new ArrayList<>();
        try {
            Date lastTime = new Date();
            for (int i = 0; i < 5; i++) {
                lastTime = JobScheduleHelper.generateNextValidTime(paramXxlJobInfo, lastTime);
                if (lastTime != null) {
                    result.add(DateUtil.formatDateTime(lastTime));
                } else {
                    break;
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new Result<List<String>>(Result.FAIL_CODE, (I18nUtil.getString("schedule_type") + I18nUtil.getString("system_unvalid")) + e.getMessage());
        }
        return new Result<List<String>>(result);

    }

}