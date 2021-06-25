package com.gls.job.admin.web.controller;

import com.gls.job.admin.web.model.JobGroup;
import com.gls.job.admin.web.service.JobGroupService;
import com.gls.job.core.api.model.Result;
import com.gls.job.core.exception.JobException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Map;

/**
 * @author george
 */
@Slf4j
@RestController
@RequestMapping("/group")
public class JobGroupController {
    @Resource
    private JobGroupService jobGroupService;

    @PostMapping("/pageList")
    public Result<Map<String, Object>> pageList(String appname, String title,
                                                @RequestParam(required = false, defaultValue = "0") int start,
                                                @RequestParam(required = false, defaultValue = "10") int length) {
        Map<String, Object> content = jobGroupService.getPageList(appname, title, start, length);
        return new Result<>(content);
    }

    @PostMapping("/add")
    public Result<String> add(@Valid JobGroup jobGroup, BindingResult result) {
        if (result.hasErrors()) {
            result.getAllErrors().forEach(objectError -> log.warn(objectError.getDefaultMessage()));
            return new Result<>(Result.FAIL_CODE, result.getAllErrors().get(0).getDefaultMessage());
        }
        try {
            jobGroupService.addJobGroup(jobGroup);
        } catch (JobException e) {
            return new Result<>(Result.FAIL_CODE, e.getMessage());
        }
        return Result.SUCCESS;
    }

    @PostMapping("/update")
    public Result<String> update(@Valid JobGroup jobGroup, BindingResult result) {
        if (result.hasErrors()) {
            result.getAllErrors().forEach(objectError -> log.warn(objectError.getDefaultMessage()));
            return new Result<>(Result.FAIL_CODE, result.getAllErrors().get(0).getDefaultMessage());
        }
        try {
            jobGroupService.updateJobGroup(jobGroup);
        } catch (JobException e) {
            return new Result<>(Result.FAIL_CODE, e.getMessage());
        }
        return Result.SUCCESS;
    }

    @PostMapping("/remove")
    public Result<String> remove(Long id) {
        try {
            jobGroupService.removeJobGroup(id);
        } catch (JobException e) {
            return new Result<>(Result.FAIL_CODE, e.getMessage());
        }
        return Result.SUCCESS;
    }

    @PostMapping("/loadById")
    public Result<JobGroup> loadById(Long id) {
        try {
            return new Result<>(jobGroupService.loadJobGroupById(id));
        } catch (JobException e) {
            return new Result<>(Result.FAIL_CODE, e.getMessage());
        }
    }
}
