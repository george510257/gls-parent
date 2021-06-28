package com.gls.job.admin.web.controller;

import com.gls.framework.api.result.Result;
import com.gls.framework.core.exception.GlsException;
import com.gls.job.admin.web.model.JobGroup;
import com.gls.job.admin.web.model.query.QueryJobGroup;
import com.gls.job.admin.web.service.JobGroupService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

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
    public Result<Page<JobGroup>> pageList(String appname, String title,
                                           @RequestParam(required = false, defaultValue = "0") int start,
                                           @RequestParam(required = false, defaultValue = "10") int length) {
        Page<JobGroup> page = jobGroupService.getPage(
                new QueryJobGroup(appname, title),
                PageRequest.of(start, length, Sort.by(Sort.Direction.ASC, "appname", "title", "id")));
        return new Result<>(page);
    }

    @PostMapping("/add")
    public Result<String> add(@Valid JobGroup jobGroup, BindingResult result) {
        if (result.hasErrors()) {
            result.getAllErrors().forEach(objectError -> log.warn(objectError.getDefaultMessage()));
            return new Result<>(Result.FAIL_CODE, result.getAllErrors().get(0).getDefaultMessage());
        }
        try {
            jobGroupService.add(jobGroup);
        } catch (GlsException e) {
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
            jobGroupService.update(jobGroup);
        } catch (GlsException e) {
            return new Result<>(Result.FAIL_CODE, e.getMessage());
        }
        return Result.SUCCESS;
    }

    @PostMapping("/remove")
    public Result<String> remove(Long id) {
        try {
            jobGroupService.remove(id);
        } catch (GlsException e) {
            return new Result<>(Result.FAIL_CODE, e.getMessage());
        }
        return Result.SUCCESS;
    }

    @PostMapping("/loadById")
    public Result<JobGroup> loadById(Long id) {
        try {
            return new Result<>(jobGroupService.getById(id));
        } catch (GlsException e) {
            return new Result<>(Result.FAIL_CODE, e.getMessage());
        }
    }
}
