package com.gls.job.admin.web.controller;

import com.gls.job.admin.core.util.I18nUtil;
import com.gls.job.admin.web.controller.annotation.PermissionLimit;
import com.gls.job.admin.web.entity.JobGroupEntity;
import com.gls.job.admin.web.entity.JobUserEntity;
import com.gls.job.admin.web.repository.JobGroupRepository;
import com.gls.job.admin.web.repository.JobUserRepository;
import com.gls.job.admin.web.service.LoginService;
import com.gls.job.core.biz.model.ReturnT;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xuxueli 2019-05-04 16:39:50
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Resource
    private JobUserRepository jobUserRepository;
    @Resource
    private JobGroupRepository jobGroupRepository;

    @RequestMapping
    @PermissionLimit(administer = true)
    public String index(Model model) {

        // 执行器列表
        List<JobGroupEntity> groupList = jobGroupRepository.findAll();
        model.addAttribute("groupList", groupList);

        return "user/user.index";
    }

    @RequestMapping("/pageList")
    @ResponseBody
    @PermissionLimit(administer = true)
    public Map<String, Object> pageList(@RequestParam(required = false, defaultValue = "0") int start,
                                        @RequestParam(required = false, defaultValue = "10") int length,
                                        String username, int role) {

        // page list
        List<JobUserEntity> list = jobUserRepository.pageList(start, length, username, role);
        int listCount = jobUserRepository.pageListCount(start, length, username, role);

        // filter
        if (list != null && list.size() > 0) {
            for (JobUserEntity item : list) {
                item.setPassword(null);
            }
        }

        // package result
        Map<String, Object> maps = new HashMap<>();
        maps.put("recordsTotal", listCount);
        // 总记录数
        maps.put("recordsFiltered", listCount);
        // 过滤后的总记录数
        maps.put("data", list);
        // 分页列表
        return maps;
    }

    @RequestMapping("/add")
    @ResponseBody
    @PermissionLimit(administer = true)
    public ReturnT<String> add(JobUserEntity jobUserEntity) {

        // valid username
        if (!StringUtils.hasText(jobUserEntity.getUsername())) {
            return new ReturnT<>(ReturnT.FAIL_CODE, I18nUtil.getString("system_please_input") + I18nUtil.getString("user_username"));
        }
        jobUserEntity.setUsername(jobUserEntity.getUsername().trim());
        if (!(jobUserEntity.getUsername().length() >= 4 && jobUserEntity.getUsername().length() <= 20)) {
            return new ReturnT<>(ReturnT.FAIL_CODE, I18nUtil.getString("system_length_limit") + "[4-20]");
        }
        // valid password
        if (!StringUtils.hasText(jobUserEntity.getPassword())) {
            return new ReturnT<>(ReturnT.FAIL_CODE, I18nUtil.getString("system_please_input") + I18nUtil.getString("user_password"));
        }
        jobUserEntity.setPassword(jobUserEntity.getPassword().trim());
        if (!(jobUserEntity.getPassword().length() >= 4 && jobUserEntity.getPassword().length() <= 20)) {
            return new ReturnT<>(ReturnT.FAIL_CODE, I18nUtil.getString("system_length_limit") + "[4-20]");
        }
        // md5 password
        jobUserEntity.setPassword(DigestUtils.md5DigestAsHex(jobUserEntity.getPassword().getBytes()));

        // check repeat
        JobUserEntity existUser = jobUserRepository.loadByUserName(jobUserEntity.getUsername());
        if (existUser != null) {
            return new ReturnT<>(ReturnT.FAIL_CODE, I18nUtil.getString("user_username_repeat"));
        }

        // write
        jobUserRepository.save(jobUserEntity);
        return ReturnT.SUCCESS;
    }

    @RequestMapping("/update")
    @ResponseBody
    @PermissionLimit(administer = true)
    public ReturnT<String> update(HttpServletRequest request, JobUserEntity jobUserEntity) {

        // avoid opt login
        JobUserEntity loginUser = (JobUserEntity) request.getAttribute(LoginService.LOGIN_IDENTITY_KEY);
        if (loginUser.getUsername().equals(jobUserEntity.getUsername())) {
            return new ReturnT<>(ReturnT.FAIL.getCode(), I18nUtil.getString("user_update_login_user_limit"));
        }

        // valid password
        if (StringUtils.hasText(jobUserEntity.getPassword())) {
            jobUserEntity.setPassword(jobUserEntity.getPassword().trim());
            if (!(jobUserEntity.getPassword().length() >= 4 && jobUserEntity.getPassword().length() <= 20)) {
                return new ReturnT<>(ReturnT.FAIL_CODE, I18nUtil.getString("system_length_limit") + "[4-20]");
            }
            // md5 password
            jobUserEntity.setPassword(DigestUtils.md5DigestAsHex(jobUserEntity.getPassword().getBytes()));
        } else {
            jobUserEntity.setPassword(null);
        }

        // write
        jobUserRepository.update(jobUserEntity);
        return ReturnT.SUCCESS;
    }

    @RequestMapping("/remove")
    @ResponseBody
    @PermissionLimit(administer = true)
    public ReturnT<String> remove(HttpServletRequest request, int id) {

        // avoid opt login 
        JobUserEntity loginUser = (JobUserEntity) request.getAttribute(LoginService.LOGIN_IDENTITY_KEY);
        if (loginUser.getId() == id) {
            return new ReturnT<>(ReturnT.FAIL.getCode(), I18nUtil.getString("user_update_login_user_limit"));
        }

        jobUserRepository.delete(id);
        return ReturnT.SUCCESS;
    }

    @RequestMapping("/updatePwd")
    @ResponseBody
    public ReturnT<String> updatePwd(HttpServletRequest request, String password) {

        // valid password
        if (password == null || password.trim().length() == 0) {
            return new ReturnT<>(ReturnT.FAIL.getCode(), "密码不可为空");
        }
        password = password.trim();
        if (!(password.length() >= 4 && password.length() <= 20)) {
            return new ReturnT<>(ReturnT.FAIL_CODE, I18nUtil.getString("system_length_limit") + "[4-20]");
        }

        // md5 password
        String md5Password = DigestUtils.md5DigestAsHex(password.getBytes());

        // update pwd
        JobUserEntity loginUser = (JobUserEntity) request.getAttribute(LoginService.LOGIN_IDENTITY_KEY);

        // do write
        JobUserEntity existUser = jobUserRepository.loadByUserName(loginUser.getUsername());
        existUser.setPassword(md5Password);
        jobUserRepository.update(existUser);

        return ReturnT.SUCCESS;
    }

}
