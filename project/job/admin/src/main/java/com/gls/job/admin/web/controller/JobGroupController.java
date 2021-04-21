package com.gls.job.admin.web.controller;

import com.gls.job.admin.core.util.I18nUtil;
import com.gls.job.admin.web.dao.XxlJobGroupDao;
import com.gls.job.admin.web.dao.XxlJobInfoDao;
import com.gls.job.admin.web.dao.XxlJobRegistryDao;
import com.gls.job.admin.web.model.XxlJobGroup;
import com.gls.job.admin.web.model.XxlJobRegistry;
import com.gls.job.core.api.model.Result;
import com.gls.job.core.constants.JobConstants;
import com.gls.job.core.enums.RegistryConfig;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * job group controller
 *
 * @author george 2016-10-02 20:52:56
 */
@Controller
@RequestMapping("/jobgroup")
public class JobGroupController {

    @Resource
    public XxlJobInfoDao glsJobInfoDao;
    @Resource
    public XxlJobGroupDao glsJobGroupDao;
    @Resource
    private XxlJobRegistryDao glsJobRegistryDao;

    @RequestMapping
    public String index(Model model) {
        return "jobgroup/jobgroup.index";
    }

    @RequestMapping("/pageList")
    @ResponseBody
    public Map<String, Object> pageList(HttpServletRequest request,
                                        @RequestParam(required = false, defaultValue = "0") int start,
                                        @RequestParam(required = false, defaultValue = "10") int length,
                                        String appname, String title) {

        // page query
        List<XxlJobGroup> list = glsJobGroupDao.pageList(start, length, appname, title);
        int list_count = glsJobGroupDao.pageListCount(start, length, appname, title);

        // package result
        Map<String, Object> maps = new HashMap<String, Object>();
        maps.put("recordsTotal", list_count);        // 总记录数
        maps.put("recordsFiltered", list_count);    // 过滤后的总记录数
        maps.put("data", list);                    // 分页列表
        return maps;
    }

    @RequestMapping("/save")
    @ResponseBody
    public Result<String> save(XxlJobGroup glsJobGroup) {

        // valid
        if (glsJobGroup.getAppname() == null || glsJobGroup.getAppname().trim().length() == 0) {
            return new Result<String>(500, (I18nUtil.getString("system_please_input") + "AppName"));
        }
        if (glsJobGroup.getAppname().length() < 4 || glsJobGroup.getAppname().length() > 64) {
            return new Result<String>(500, I18nUtil.getString("jobgroup_field_appname_length"));
        }
        if (glsJobGroup.getAppname().contains(">") || glsJobGroup.getAppname().contains("<")) {
            return new Result<String>(500, "AppName" + I18nUtil.getString("system_unvalid"));
        }
        if (glsJobGroup.getTitle() == null || glsJobGroup.getTitle().trim().length() == 0) {
            return new Result<String>(500, (I18nUtil.getString("system_please_input") + I18nUtil.getString("jobgroup_field_title")));
        }
        if (glsJobGroup.getTitle().contains(">") || glsJobGroup.getTitle().contains("<")) {
            return new Result<String>(500, I18nUtil.getString("jobgroup_field_title") + I18nUtil.getString("system_unvalid"));
        }
        if (glsJobGroup.getAddressType() != 0) {
            if (glsJobGroup.getAddressList() == null || glsJobGroup.getAddressList().trim().length() == 0) {
                return new Result<String>(500, I18nUtil.getString("jobgroup_field_addressType_limit"));
            }
            if (glsJobGroup.getAddressList().contains(">") || glsJobGroup.getAddressList().contains("<")) {
                return new Result<String>(500, I18nUtil.getString("jobgroup_field_registryList") + I18nUtil.getString("system_unvalid"));
            }

            String[] addresss = glsJobGroup.getAddressList().split(",");
            for (String item : addresss) {
                if (item == null || item.trim().length() == 0) {
                    return new Result<String>(500, I18nUtil.getString("jobgroup_field_registryList_unvalid"));
                }
            }
        }

        // process
        glsJobGroup.setUpdateTime(new Date());

        int ret = glsJobGroupDao.save(glsJobGroup);
        return (ret > 0) ? Result.SUCCESS : Result.FAIL;
    }

    @RequestMapping("/update")
    @ResponseBody
    public Result<String> update(XxlJobGroup glsJobGroup) {
        // valid
        if (glsJobGroup.getAppname() == null || glsJobGroup.getAppname().trim().length() == 0) {
            return new Result<String>(500, (I18nUtil.getString("system_please_input") + "AppName"));
        }
        if (glsJobGroup.getAppname().length() < 4 || glsJobGroup.getAppname().length() > 64) {
            return new Result<String>(500, I18nUtil.getString("jobgroup_field_appname_length"));
        }
        if (glsJobGroup.getTitle() == null || glsJobGroup.getTitle().trim().length() == 0) {
            return new Result<String>(500, (I18nUtil.getString("system_please_input") + I18nUtil.getString("jobgroup_field_title")));
        }
        if (glsJobGroup.getAddressType() == 0) {
            // 0=自动注册
            List<String> registryList = findRegistryByAppName(glsJobGroup.getAppname());
            String addressListStr = null;
            if (registryList != null && !registryList.isEmpty()) {
                Collections.sort(registryList);
                addressListStr = "";
                for (String item : registryList) {
                    addressListStr += item + ",";
                }
                addressListStr = addressListStr.substring(0, addressListStr.length() - 1);
            }
            glsJobGroup.setAddressList(addressListStr);
        } else {
            // 1=手动录入
            if (glsJobGroup.getAddressList() == null || glsJobGroup.getAddressList().trim().length() == 0) {
                return new Result<String>(500, I18nUtil.getString("jobgroup_field_addressType_limit"));
            }
            String[] addresss = glsJobGroup.getAddressList().split(",");
            for (String item : addresss) {
                if (item == null || item.trim().length() == 0) {
                    return new Result<String>(500, I18nUtil.getString("jobgroup_field_registryList_unvalid"));
                }
            }
        }

        // process
        glsJobGroup.setUpdateTime(new Date());

        int ret = glsJobGroupDao.update(glsJobGroup);
        return (ret > 0) ? Result.SUCCESS : Result.FAIL;
    }

    private List<String> findRegistryByAppName(String appnameParam) {
        HashMap<String, List<String>> appAddressMap = new HashMap<String, List<String>>();
        List<XxlJobRegistry> list = glsJobRegistryDao.findAll(JobConstants.DEAD_TIMEOUT, new Date());
        if (list != null) {
            for (XxlJobRegistry item : list) {
                if (RegistryConfig.RegistType.EXECUTOR.name().equals(item.getRegistryGroup())) {
                    String appname = item.getRegistryKey();
                    List<String> registryList = appAddressMap.get(appname);
                    if (registryList == null) {
                        registryList = new ArrayList<String>();
                    }

                    if (!registryList.contains(item.getRegistryValue())) {
                        registryList.add(item.getRegistryValue());
                    }
                    appAddressMap.put(appname, registryList);
                }
            }
        }
        return appAddressMap.get(appnameParam);
    }

    @RequestMapping("/remove")
    @ResponseBody
    public Result<String> remove(int id) {

        // valid
        int count = glsJobInfoDao.pageListCount(0, 10, id, -1, null, null, null);
        if (count > 0) {
            return new Result<String>(500, I18nUtil.getString("jobgroup_del_limit_0"));
        }

        List<XxlJobGroup> allList = glsJobGroupDao.findAll();
        if (allList.size() == 1) {
            return new Result<String>(500, I18nUtil.getString("jobgroup_del_limit_1"));
        }

        int ret = glsJobGroupDao.remove(id);
        return (ret > 0) ? Result.SUCCESS : Result.FAIL;
    }

    @RequestMapping("/loadById")
    @ResponseBody
    public Result<XxlJobGroup> loadById(int id) {
        XxlJobGroup jobGroup = glsJobGroupDao.load(id);
        return jobGroup != null ? new Result<XxlJobGroup>(jobGroup) : new Result<XxlJobGroup>(Result.FAIL_CODE, null);
    }

}
