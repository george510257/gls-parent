package com.gls.job.admin.web.controller;

import com.gls.framework.api.result.Result;
import com.gls.job.admin.web.model.JobGroupModel;
import com.gls.job.admin.web.model.query.QueryJobGroup;
import com.gls.job.admin.web.service.JobGroupService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @PostMapping("/pageList")
    public Result<Page<JobGroupModel>> pageList(QueryJobGroup queryJobGroup, Pageable pageable) {
        Page<JobGroupModel> page = jobGroupService.getPage(queryJobGroup, pageable);
        return new Result<>(page);
    }

    @PostMapping("/add")
    public Result<String> add(@Valid JobGroupModel jobGroupModel) {
        jobGroupService.add(jobGroupModel);
        return Result.SUCCESS;
    }

    @PostMapping("/update")
    public Result<String> update(@Valid JobGroupModel jobGroupModel) {
        jobGroupService.update(jobGroupModel);
        return Result.SUCCESS;
    }

    @PostMapping("/remove")
    public Result<String> remove(Long id) {
        jobGroupService.remove(id);
        return Result.SUCCESS;
    }

    @PostMapping("/loadById")
    public Result<JobGroupModel> loadById(Long id) {
        return new Result<>(jobGroupService.getById(id));
    }
}
