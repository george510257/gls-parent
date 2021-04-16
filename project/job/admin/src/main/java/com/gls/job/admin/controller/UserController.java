package com.gls.job.admin.controller;

import com.gls.job.admin.controller.annotation.PermissionLimit;
import com.gls.job.admin.core.model.XxlJobGroup;
import com.gls.job.admin.core.model.XxlJobUser;
import com.gls.job.admin.core.util.I18nUtil;
import com.gls.job.admin.dao.XxlJobGroupDao;
import com.gls.job.admin.dao.XxlJobUserDao;
import com.gls.job.admin.service.LoginService;
import com.gls.job.core.api.model.Result;
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
    private XxlJobUserDao glsJobUserDao;
    @Resource
    private XxlJobGroupDao glsJobGroupDao;

    @RequestMapping
    @PermissionLimit(adminuser = true)
    public String index(Model model) {

        // 执行器列表
        List<XxlJobGroup> groupList = glsJobGroupDao.findAll();
        model.addAttribute("groupList", groupList);

        return "user/user.index";
    }

    @RequestMapping("/pageList")
    @ResponseBody
    @PermissionLimit(adminuser = true)
    public Map<String, Object> pageList(@RequestParam(required = false, defaultValue = "0") int start,
                                        @RequestParam(required = false, defaultValue = "10") int length,
                                        String username, int role) {

        // page list
        List<XxlJobUser> list = glsJobUserDao.pageList(start, length, username, role);
        int list_count = glsJobUserDao.pageListCount(start, length, username, role);

        // filter
        if (list != null && list.size() > 0) {
            for (XxlJobUser item : list) {
                item.setPassword(null);
            }
        }

        // package result
        Map<String, Object> maps = new HashMap<String, Object>();
        maps.put("recordsTotal", list_count);        // 总记录数
        maps.put("recordsFiltered", list_count);    // 过滤后的总记录数
        maps.put("data", list);                    // 分页列表
        return maps;
    }

    @RequestMapping("/add")
    @ResponseBody
    @PermissionLimit(adminuser = true)
    public Result<String> add(XxlJobUser glsJobUser) {

        // valid username
        if (!StringUtils.hasText(glsJobUser.getUsername())) {
            return new Result<String>(Result.FAIL_CODE, I18nUtil.getString("system_please_input") + I18nUtil.getString("user_username"));
        }
        glsJobUser.setUsername(glsJobUser.getUsername().trim());
        if (!(glsJobUser.getUsername().length() >= 4 && glsJobUser.getUsername().length() <= 20)) {
            return new Result<String>(Result.FAIL_CODE, I18nUtil.getString("system_lengh_limit") + "[4-20]");
        }
        // valid password
        if (!StringUtils.hasText(glsJobUser.getPassword())) {
            return new Result<String>(Result.FAIL_CODE, I18nUtil.getString("system_please_input") + I18nUtil.getString("user_password"));
        }
        glsJobUser.setPassword(glsJobUser.getPassword().trim());
        if (!(glsJobUser.getPassword().length() >= 4 && glsJobUser.getPassword().length() <= 20)) {
            return new Result<String>(Result.FAIL_CODE, I18nUtil.getString("system_lengh_limit") + "[4-20]");
        }
        // md5 password
        glsJobUser.setPassword(DigestUtils.md5DigestAsHex(glsJobUser.getPassword().getBytes()));

        // check repeat
        XxlJobUser existUser = glsJobUserDao.loadByUserName(glsJobUser.getUsername());
        if (existUser != null) {
            return new Result<String>(Result.FAIL_CODE, I18nUtil.getString("user_username_repeat"));
        }

        // write
        glsJobUserDao.save(glsJobUser);
        return Result.SUCCESS;
    }

    @RequestMapping("/update")
    @ResponseBody
    @PermissionLimit(adminuser = true)
    public Result<String> update(HttpServletRequest request, XxlJobUser glsJobUser) {

        // avoid opt login seft
        XxlJobUser loginUser = (XxlJobUser) request.getAttribute(LoginService.LOGIN_IDENTITY_KEY);
        if (loginUser.getUsername().equals(glsJobUser.getUsername())) {
            return new Result<String>(Result.FAIL.getCode(), I18nUtil.getString("user_update_loginuser_limit"));
        }

        // valid password
        if (StringUtils.hasText(glsJobUser.getPassword())) {
            glsJobUser.setPassword(glsJobUser.getPassword().trim());
            if (!(glsJobUser.getPassword().length() >= 4 && glsJobUser.getPassword().length() <= 20)) {
                return new Result<String>(Result.FAIL_CODE, I18nUtil.getString("system_lengh_limit") + "[4-20]");
            }
            // md5 password
            glsJobUser.setPassword(DigestUtils.md5DigestAsHex(glsJobUser.getPassword().getBytes()));
        } else {
            glsJobUser.setPassword(null);
        }

        // write
        glsJobUserDao.update(glsJobUser);
        return Result.SUCCESS;
    }

    @RequestMapping("/remove")
    @ResponseBody
    @PermissionLimit(adminuser = true)
    public Result<String> remove(HttpServletRequest request, int id) {

        // avoid opt login seft
        XxlJobUser loginUser = (XxlJobUser) request.getAttribute(LoginService.LOGIN_IDENTITY_KEY);
        if (loginUser.getId() == id) {
            return new Result<String>(Result.FAIL.getCode(), I18nUtil.getString("user_update_loginuser_limit"));
        }

        glsJobUserDao.delete(id);
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
            return new Result<String>(Result.FAIL_CODE, I18nUtil.getString("system_lengh_limit") + "[4-20]");
        }

        // md5 password
        String md5Password = DigestUtils.md5DigestAsHex(password.getBytes());

        // update pwd
        XxlJobUser loginUser = (XxlJobUser) request.getAttribute(LoginService.LOGIN_IDENTITY_KEY);

        // do write
        XxlJobUser existUser = glsJobUserDao.loadByUserName(loginUser.getUsername());
        existUser.setPassword(md5Password);
        glsJobUserDao.update(existUser);

        return Result.SUCCESS;
    }

}
