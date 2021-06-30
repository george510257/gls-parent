package com.gls.job.admin.web.controller;

import com.gls.framework.api.result.Result;
import com.gls.job.admin.web.model.JobGroup;
import com.gls.job.admin.web.model.query.QueryJobGroup;
import com.gls.job.admin.web.service.JobGroupService;
import com.gls.job.admin.web.validator.JobGroupValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
    @Resource
    private JobGroupValidator jobGroupValidator;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(jobGroupValidator);
    }

    @PostMapping("/pageList")
    public Result<Page<JobGroup>> pageList(QueryJobGroup queryJobGroup, Pageable pageable) {
        return new Result<>(jobGroupService.getPage(queryJobGroup, pageable));
    }

    @PostMapping("/add")
    public Result<String> add(@Valid JobGroup jobGroup) {
        jobGroupService.add(jobGroup);
        return Result.SUCCESS;
    }

    @PostMapping("/remove")
    public Result<String> remove(Long id) {
        jobGroupService.remove(id);
        return Result.SUCCESS;
    }

    @PostMapping("/update")
    public Result<String> update(@Valid JobGroup jobGroup) {
        jobGroupService.update(jobGroup);
        return Result.SUCCESS;
    }

    @PostMapping("/loadById")
    public Result<JobGroup> loadById(Long id) {
        return new Result<>(jobGroupService.getById(id));
    }
}
