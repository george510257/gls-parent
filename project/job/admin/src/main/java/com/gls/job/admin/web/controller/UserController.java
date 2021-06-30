package com.gls.job.admin.web.controller;

import com.gls.framework.api.result.Result;
import com.gls.job.admin.core.servlet.PermissionLimit;
import com.gls.job.admin.web.model.JobGroup;
import com.gls.job.admin.web.model.JobUser;
import com.gls.job.admin.web.model.query.QueryJobUser;
import com.gls.job.admin.web.service.JobGroupService;
import com.gls.job.admin.web.service.JobUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xuxueli 2019-05-04 16:39:50
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private JobUserService jobUserService;
    @Resource
    private JobGroupService jobGroupService;

    @PermissionLimit(administer = true)
    @PostMapping("/add")
    public Result<String> add(@Valid JobUser jobUser) {
        jobUserService.add(jobUser);
        return Result.SUCCESS;
    }

    @PermissionLimit(administer = true)
    @PostMapping("/changePassword")
    public Result<String> changePassword(String password) {
        jobUserService.changePassword(password);
        return Result.SUCCESS;
    }

    @PermissionLimit(administer = true)
    @GetMapping("/index")
    public Result<Map<String, Object>> index() {
        Map<String, Object> map = new HashMap<>();
        List<JobGroup> jobGroupList = jobGroupService.getAll();
        map.put("groupList", jobGroupList);
        return new Result<>(map);
    }

    @PermissionLimit(limit = false)
    @PostMapping("/login")
    public Result<String> login(String username, String password, Boolean ifRemember) {
        jobUserService.login(username, password, ifRemember);
        return Result.SUCCESS;
    }

    @PermissionLimit(limit = false)
    @PostMapping("/logout")
    public Result<String> logout() {
        jobUserService.logout();
        return Result.SUCCESS;
    }

    @PermissionLimit(administer = true)
    @GetMapping("/pageList")
    public Result<Page<JobUser>> pageList(QueryJobUser queryJobUser, Pageable pageable) {
        Page<JobUser> page = jobUserService.getPage(queryJobUser, pageable);
        return new Result<>(page);
    }

    @PermissionLimit(administer = true)
    @PostMapping("/remove")
    public Result<String> remove(Long id) {
        jobUserService.remove(id);
        return Result.SUCCESS;
    }

    @PermissionLimit(administer = true)
    @PostMapping("/update")
    public Result<String> update(@Valid JobUser jobUser) {
        jobUserService.update(jobUser);
        return Result.SUCCESS;
    }
}
