package com.gls.job.admin.web.controller;

import com.gls.job.admin.core.i18n.I18nHelper;
import com.gls.job.admin.web.controller.annotation.PermissionLimit;
import com.gls.job.admin.web.dao.JobGroupDao;
import com.gls.job.admin.web.dao.JobUserDao;
import com.gls.job.admin.web.model.JobGroup;
import com.gls.job.admin.web.model.JobUser;
import com.gls.job.admin.web.service.JobGroupService;
import com.gls.job.admin.web.service.JobUserService;
import com.gls.job.core.api.model.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.DigestUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * @author xuxueli 2019-05-04 16:39:50
 */
@Slf4j
@Controller
@RequestMapping("/user")
public class UserController {

    @Resource
    private JobUserService jobUserService;
    @Resource
    private JobGroupService jobGroupService;

    @Resource
    private JobUserDao jobUserDao;
    @Resource
    private JobGroupDao jobGroupDao;
    @Resource
    private I18nHelper i18nHelper;

    @RequestMapping
    @PermissionLimit(administer = true)
    public String index(Model model) {

        // 执行器列表
        List<JobGroup> groupList = jobGroupService.getAllGroupList();
        model.addAttribute("groupList", groupList);

        return "user/user.index";
    }

    @RequestMapping("/pageList")
    @ResponseBody
    @PermissionLimit(administer = true)
    public Map<String, Object> pageList(@RequestParam(required = false, defaultValue = "0") int start,
                                        @RequestParam(required = false, defaultValue = "10") int length,
                                        String username, int role) {

        return jobUserService.getPageList(username, role, start, length);
    }

    @RequestMapping("/add")
    @ResponseBody
    @PermissionLimit(administer = true)
    public Result<String> add(@Valid JobUser jobUser, BindingResult errors) {
        if (errors.hasErrors()) {
            errors.getAllErrors().forEach(objectError -> log.error(objectError.getDefaultMessage()));
            return new Result<>(Result.FAIL_CODE, errors.getAllErrors().get(0).getDefaultMessage());
        }
        boolean flag = jobUserService.addUser(jobUser);
        if (flag) {
            return Result.SUCCESS;
        } else {
            return new Result<>(Result.FAIL_CODE, i18nHelper.getString("user_username_repeat"));
        }
    }

    @RequestMapping("/update")
    @ResponseBody
    @PermissionLimit(administer = true)
    public Result<String> update(HttpServletRequest request, @Valid JobUser jobUser, BindingResult errors) {
        if (errors.hasErrors()) {
            errors.getAllErrors().forEach(objectError -> log.error(objectError.getDefaultMessage()));
            return new Result<>(Result.FAIL_CODE, errors.getAllErrors().get(0).getDefaultMessage());
        }
        // avoid opt login seft
        JobUser loginUser = (JobUser) request.getAttribute(JobUserService.LOGIN_IDENTITY_KEY);
        if (loginUser.getUsername().equals(jobUser.getUsername())) {
            return new Result<>(Result.FAIL.getCode(), i18nHelper.getString("user_update_loginuser_limit"));
        }

        // write
        jobUserService.updateUser(jobUser);
        return Result.SUCCESS;
    }

    @RequestMapping("/remove")
    @ResponseBody
    @PermissionLimit(administer = true)
    public Result<String> remove(HttpServletRequest request, Long id) {

        // avoid opt login seft
        JobUser loginUser = (JobUser) request.getAttribute(JobUserService.LOGIN_IDENTITY_KEY);
        if (loginUser.getId().equals(id)) {
            return new Result<String>(Result.FAIL.getCode(), i18nHelper.getString("user_update_loginuser_limit"));
        }

        jobUserDao.delete(id);
        return Result.SUCCESS;
    }

    @RequestMapping("/updatePwd")
    @ResponseBody
    public Result<String> updatePwd(HttpServletRequest request, String password) {

        // valid password
        if (password == null || password.trim().length() == 0) {
            return new Result<String>(Result.FAIL.getCode(), "密码不可为空");
        }
        password = password.trim();
        if (!(password.length() >= 4 && password.length() <= 20)) {
            return new Result<String>(Result.FAIL_CODE, i18nHelper.getString("system_length_limit") + "[4-20]");
        }

        // md5 password
        String md5Password = DigestUtils.md5DigestAsHex(password.getBytes());

        // update pwd
        JobUser loginUser = (JobUser) request.getAttribute(JobUserService.LOGIN_IDENTITY_KEY);

        // do write
        JobUser existUser = jobUserDao.loadByUserName(loginUser.getUsername());
        existUser.setPassword(md5Password);
        jobUserDao.update(existUser);

        return Result.SUCCESS;
    }

}
