package com.gls.job.admin.web.controller;

import com.gls.framework.api.result.Result;
import com.gls.framework.core.util.StringUtil;
import com.gls.job.admin.constants.ExecutorRouteStrategy;
import com.gls.job.admin.constants.MisfireStrategy;
import com.gls.job.admin.constants.ScheduleType;
import com.gls.job.admin.constants.TriggerType;
import com.gls.job.admin.core.support.JobScheduleHelper;
import com.gls.job.admin.web.model.JobInfo;
import com.gls.job.admin.web.model.query.QueryJobInfo;
import com.gls.job.admin.web.service.AsyncService;
import com.gls.job.admin.web.service.JobGroupService;
import com.gls.job.admin.web.service.JobInfoService;
import com.gls.job.core.constants.ExecutorBlockStrategy;
import com.gls.job.core.constants.GlueType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author georg
 */
@Slf4j
@RestController
@RequestMapping("/info")
public class JobInfoController {
    @Resource
    private AsyncService asyncService;
    @Resource
    private JobInfoService jobInfoService;
    @Resource
    private JobGroupService jobGroupService;

    @GetMapping("/index")
    public Result<Map<String, Object>> index(@RequestParam(required = false, defaultValue = "-1") Long jobGroupId) {
        Map<String, Object> maps = new HashMap<>();
        maps.put("ExecutorRouteStrategy", ExecutorRouteStrategy.values());
        maps.put("GlueType", GlueType.values());
        maps.put("ExecutorBlockStrategy", ExecutorBlockStrategy.values());
        maps.put("ScheduleType", ScheduleType.values());
        maps.put("MisfireStrategy", MisfireStrategy.values());
        maps.put("JobGroupList", jobGroupService.getByLoginUser());
        maps.put("jobGroup", jobGroupId);
        return new Result<>(maps);
    }

    @PostMapping("/pageList")
    public Result<Page<JobInfo>> pageList(QueryJobInfo queryJobInfo, Pageable pageable) {
        return new Result<>(jobInfoService.getPage(queryJobInfo, pageable));
    }

    @PostMapping("/add")
    public Result<String> add(JobInfo jobInfo) {
        jobInfoService.add(jobInfo);
        return Result.SUCCESS;
    }

    @GetMapping("/remove")
    public Result<String> remove(Long jobInfoId) {
        jobInfoService.remove(jobInfoId);
        return Result.SUCCESS;
    }

    @PostMapping("/update")
    public Result<String> update(JobInfo jobInfo) {
        jobInfoService.update(jobInfo);
        return Result.SUCCESS;
    }

    @GetMapping("/start")
    public Result<String> start(Long jobInfoId) {
        jobInfoService.start(jobInfoId);
        return Result.SUCCESS;
    }

    @GetMapping("/stop")
    public Result<String> stop(Long jobInfoId) {
        jobInfoService.stop(jobInfoId);
        return Result.SUCCESS;
    }

    @PostMapping("/trigger")
    public Result<String> trigger(Long jobInfoId, String executorParam, String addressList) {
        asyncService.asyncTrigger(jobInfoId, TriggerType.MANUAL, -1, null, executorParam, StringUtil.toList(addressList));
        return Result.SUCCESS;
    }

    @PostMapping("/nextTriggerTime")
    public Result<List<String>> nextTriggerTime(String scheduleType, String scheduleConf) {
        List<String> list = JobScheduleHelper.nextTriggerTime(scheduleType, scheduleConf, 6);
        return new Result<>(list);
    }
}
