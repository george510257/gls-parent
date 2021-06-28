package com.gls.job.admin.web.controller;

import com.gls.framework.api.result.Result;
import com.gls.framework.core.exception.GlsException;
import com.gls.job.admin.core.servlet.PermissionLimit;
import com.gls.job.admin.core.util.LoginUserUtil;
import com.gls.job.admin.web.model.JobGroup;
import com.gls.job.admin.web.model.JobUser;
import com.gls.job.admin.web.model.query.QueryJobUser;
import com.gls.job.admin.web.service.JobGroupService;
import com.gls.job.admin.web.service.JobUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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

    @PermissionLimit(limit = false)
    @PostMapping("/login")
    public Result<String> login(String userName, String password, String ifRemember) {
        boolean ifRem = ifRemember != null && ifRemember.trim().length() > 0 && "on".equals(ifRemember);
        String message = jobUserService.login(userName, password, ifRem);
        if (StringUtils.hasText(message)) {
            new Result<String>(500, message);
        }
        return Result.SUCCESS;
    }

    @PermissionLimit(limit = false)
    @PostMapping("/logout")
    public Result<String> logout() {
        String message = jobUserService.logout();
        if (StringUtils.hasText(message)) {
            new Result<String>(500, message);
        }
        return Result.SUCCESS;
    }

    @PermissionLimit(administer = true)
    @GetMapping("/group")
    public Result<List<JobGroup>> group() {
        List<JobGroup> jobGroupList = jobGroupService.getAll();
        return new Result<>(jobGroupList);
    }

    @PermissionLimit(administer = true)
    @GetMapping("/pageList")
    public Result<Map<String, Object>> pageList(String username, int role,
                                                @RequestParam(required = false, defaultValue = "0") int start,
                                                @RequestParam(required = false, defaultValue = "10") int length) {
        Page<JobUser> page = jobUserService.getPage(new QueryJobUser(username, role), PageRequest.of(start, length, Sort.by(Sort.Direction.ASC, "username")));
        Map<String, Object> map = new HashMap<>(3);
        // 总记录数
        map.put("recordsTotal", page.getTotalElements());
        // 过滤后的总记录数
        map.put("recordsFiltered", page.getTotalElements());
        // 分页列表
        map.put("data", page.getContent());
        return new Result<>(map);
    }

    @PermissionLimit(administer = true)
    @PostMapping("/add")
    public Result<String> addUser(@Valid JobUser jobUser, BindingResult result) {
        if (result.hasErrors()) {
            result.getAllErrors().forEach(objectError -> log.warn(objectError.getDefaultMessage()));
            return new Result<>(Result.FAIL_CODE, result.getAllErrors().get(0).getDefaultMessage());
        }
        try {
            jobUserService.add(jobUser);
        } catch (GlsException e) {
            return new Result<>(Result.FAIL_CODE, e.getMessage());
        }
        return Result.SUCCESS;
    }

    @PermissionLimit(administer = true)
    @PostMapping("/update")
    public Result<String> updateUser(@Valid JobUser jobUser, BindingResult result) {
        if (result.hasErrors()) {
            result.getAllErrors().forEach(objectError -> log.warn(objectError.getDefaultMessage()));
            return new Result<>(Result.FAIL_CODE, result.getAllErrors().get(0).getDefaultMessage());
        }
        JobUser loginUser = LoginUserUtil.getLoginUser();
        if (loginUser.getUsername().equals(jobUser.getUsername())) {
            return new Result<>(Result.FAIL_CODE, "禁止操作当前登录账号");
        }
        try {
            jobUserService.update(jobUser);
        } catch (GlsException e) {
            return new Result<>(Result.FAIL_CODE, e.getMessage());
        }
        return Result.SUCCESS;
    }

    @PermissionLimit(administer = true)
    @PostMapping("/remove")
    public Result<String> removeUser(Long id) {
        JobUser loginUser = LoginUserUtil.getLoginUser();
        if (loginUser.getId().equals(id)) {
            return new Result<>(Result.FAIL_CODE, "禁止操作当前登录账号");
        }
        try {
            jobUserService.remove(id);
        } catch (GlsException e) {
            return new Result<>(Result.FAIL_CODE, e.getMessage());
        }
        return Result.SUCCESS;
    }

    @PermissionLimit(administer = true)
    @PostMapping("/updatePwd")
    public Result<String> changePassword(String password) {
        if (!StringUtils.hasText(password)) {
            return new Result<>(Result.FAIL_CODE, "密码不可为空");
        }
        if (password.trim().length() < 4 || password.trim().length() > 20) {
            return new Result<>(Result.FAIL_CODE, "长度限制[4-20]");
        }
        try {
            jobUserService.changePassword(password);
        } catch (GlsException e) {
            return new Result<>(Result.FAIL_CODE, e.getMessage());
        }
        return Result.SUCCESS;
    }
}
