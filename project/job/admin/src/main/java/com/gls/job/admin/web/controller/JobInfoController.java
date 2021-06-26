package com.gls.job.admin.web.controller;

import com.gls.framework.api.result.Result;
import com.gls.framework.core.util.StringUtil;
import com.gls.job.admin.constants.TriggerType;
import com.gls.job.admin.web.model.JobInfo;
import com.gls.job.admin.web.service.JobAsyncService;
import com.gls.job.admin.web.service.JobInfoService;
import com.gls.job.core.exception.JobException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
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
    private JobAsyncService jobAsyncService;

    @Resource
    private JobInfoService jobInfoService;

    @GetMapping("/index")
    public Result<Map<String, Object>> index(@RequestParam(required = false, defaultValue = "-1") Long jobGroupId) {
        try {
            return new Result<>(jobInfoService.getIndexData(jobGroupId));
        } catch (JobException e) {
            return new Result<>(Result.FAIL_CODE, e.getMessage());
        }
    }

    @PostMapping("/pageList")
    public Result<Map<String, Object>> pageList(Long jobGroup, Boolean triggerStatus, String jobDesc, String executorHandler, String author,
                                                @RequestParam(required = false, defaultValue = "0") int start,
                                                @RequestParam(required = false, defaultValue = "10") int length) {
        try {
            return new Result<>(jobInfoService.pageList(jobGroup, triggerStatus, jobDesc, executorHandler, author, start, length));
        } catch (JobException e) {
            return new Result<>(Result.FAIL_CODE, e.getMessage());
        }
    }

    @PostMapping("/add")
    public Result<String> add(JobInfo jobInfo) {
        try {
            jobInfoService.addJobInfo(jobInfo);
            return Result.SUCCESS;
        } catch (JobException e) {
            return new Result<>(Result.FAIL_CODE, e.getMessage());
        }
    }

    @PostMapping("/update")
    public Result<String> update(JobInfo jobInfo) {
        try {
            jobInfoService.updateJobInfo(jobInfo);
            return Result.SUCCESS;
        } catch (JobException e) {
            return new Result<>(Result.FAIL_CODE, e.getMessage());
        }
    }

    @GetMapping("/remove")
    public Result<String> remove(Long jobInfoId) {
        try {
            jobInfoService.removeJobInfo(jobInfoId);
            return Result.SUCCESS;
        } catch (JobException e) {
            return new Result<>(Result.FAIL_CODE, e.getMessage());
        }
    }

    @GetMapping("/stop")
    public Result<String> stop(Long jobInfoId) {
        try {
            jobInfoService.stopJobInfo(jobInfoId);
            return Result.SUCCESS;
        } catch (JobException e) {
            return new Result<>(Result.FAIL_CODE, e.getMessage());
        }
    }

    @GetMapping("/start")
    public Result<String> start(Long jobInfoId) {
        try {
            jobInfoService.startJobInfo(jobInfoId);
            return Result.SUCCESS;
        } catch (JobException e) {
            return new Result<>(Result.FAIL_CODE, e.getMessage());
        }
    }

    @PostMapping("/trigger")
    public Result<String> trigger(Long jobInfoId, String executorParam, String addressList) {
        try {
            jobAsyncService.asyncTrigger(jobInfoId, TriggerType.MANUAL, -1, null, executorParam, StringUtil.toList(addressList));
            return Result.SUCCESS;
        } catch (JobException e) {
            return new Result<>(Result.FAIL_CODE, e.getMessage());
        }
    }

    @PostMapping("/nextTriggerTime")
    public Result<List<String>> nextTriggerTime(String scheduleType, String scheduleConf) {
        try {
            List<String> list = jobInfoService.nextTriggerTime(scheduleType, scheduleConf);
            return new Result<>(list);
        } catch (JobException e) {
            return new Result<>(Result.FAIL_CODE, e.getMessage());
        }
    }
}
