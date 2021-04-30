package com.gls.job.admin.web.controller;

import com.gls.job.admin.core.util.I18nUtil;
import com.gls.job.admin.web.dao.JobGroupDao;
import com.gls.job.admin.web.dao.JobInfoDao;
import com.gls.job.admin.web.dao.JobRegistryDao;
import com.gls.job.admin.web.entity.JobGroup;
import com.gls.job.admin.web.entity.JobRegistry;
import com.gls.job.core.api.model.Result;
import com.gls.job.core.api.model.enums.RegistryType;
import com.gls.job.core.constants.JobConstants;
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
    public JobInfoDao jobInfoDao;
    @Resource
    public JobGroupDao jobGroupDao;
    @Resource
    private JobRegistryDao jobRegistryDao;

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
        List<JobGroup> list = jobGroupDao.pageList(start, length, appname, title);
        int list_count = jobGroupDao.pageListCount(start, length, appname, title);

        // package result
        Map<String, Object> maps = new HashMap<String, Object>();
        maps.put("recordsTotal", list_count);        // 总记录数
        maps.put("recordsFiltered", list_count);    // 过滤后的总记录数
        maps.put("data", list);                    // 分页列表
        return maps;
    }

    @RequestMapping("/save")
    @ResponseBody
    public Result<String> save(JobGroup jobGroup) {

        // valid
        if (jobGroup.getAppname() == null || jobGroup.getAppname().trim().length() == 0) {
            return new Result<String>(500, (I18nUtil.getString("system_please_input") + "AppName"));
        }
        if (jobGroup.getAppname().length() < 4 || jobGroup.getAppname().length() > 64) {
            return new Result<String>(500, I18nUtil.getString("jobgroup_field_appname_length"));
        }
        if (jobGroup.getAppname().contains(">") || jobGroup.getAppname().contains("<")) {
            return new Result<String>(500, "AppName" + I18nUtil.getString("system_unvalid"));
        }
        if (jobGroup.getTitle() == null || jobGroup.getTitle().trim().length() == 0) {
            return new Result<String>(500, (I18nUtil.getString("system_please_input") + I18nUtil.getString("jobgroup_field_title")));
        }
        if (jobGroup.getTitle().contains(">") || jobGroup.getTitle().contains("<")) {
            return new Result<String>(500, I18nUtil.getString("jobgroup_field_title") + I18nUtil.getString("system_unvalid"));
        }
        if (jobGroup.getAddressType() != 0) {
            if (jobGroup.getAddressList() == null || jobGroup.getAddressList().trim().length() == 0) {
                return new Result<String>(500, I18nUtil.getString("jobgroup_field_addressType_limit"));
            }
            if (jobGroup.getAddressList().contains(">") || jobGroup.getAddressList().contains("<")) {
                return new Result<String>(500, I18nUtil.getString("jobgroup_field_registryList") + I18nUtil.getString("system_unvalid"));
            }

            String[] addresss = jobGroup.getAddressList().split(",");
            for (String item : addresss) {
                if (item == null || item.trim().length() == 0) {
                    return new Result<String>(500, I18nUtil.getString("jobgroup_field_registryList_unvalid"));
                }
            }
        }

        // process
        jobGroup.setUpdateTime(new Date());

        int ret = jobGroupDao.save(jobGroup);
        return (ret > 0) ? Result.SUCCESS : Result.FAIL;
    }

    @RequestMapping("/update")
    @ResponseBody
    public Result<String> update(JobGroup jobGroup) {
        // valid
        if (jobGroup.getAppname() == null || jobGroup.getAppname().trim().length() == 0) {
            return new Result<String>(500, (I18nUtil.getString("system_please_input") + "AppName"));
        }
        if (jobGroup.getAppname().length() < 4 || jobGroup.getAppname().length() > 64) {
            return new Result<String>(500, I18nUtil.getString("jobgroup_field_appname_length"));
        }
        if (jobGroup.getTitle() == null || jobGroup.getTitle().trim().length() == 0) {
            return new Result<String>(500, (I18nUtil.getString("system_please_input") + I18nUtil.getString("jobgroup_field_title")));
        }
        if (jobGroup.getAddressType() == 0) {
            // 0=自动注册
            List<String> registryList = findRegistryByAppName(jobGroup.getAppname());
            String addressListStr = null;
            if (registryList != null && !registryList.isEmpty()) {
                Collections.sort(registryList);
                addressListStr = "";
                for (String item : registryList) {
                    addressListStr += item + ",";
                }
                addressListStr = addressListStr.substring(0, addressListStr.length() - 1);
            }
            jobGroup.setAddressList(addressListStr);
        } else {
            // 1=手动录入
            if (jobGroup.getAddressList() == null || jobGroup.getAddressList().trim().length() == 0) {
                return new Result<String>(500, I18nUtil.getString("jobgroup_field_addressType_limit"));
            }
            String[] addresss = jobGroup.getAddressList().split(",");
            for (String item : addresss) {
                if (item == null || item.trim().length() == 0) {
                    return new Result<String>(500, I18nUtil.getString("jobgroup_field_registryList_unvalid"));
                }
            }
        }

        // process
        jobGroup.setUpdateTime(new Date());

        int ret = jobGroupDao.update(jobGroup);
        return (ret > 0) ? Result.SUCCESS : Result.FAIL;
    }

    private List<String> findRegistryByAppName(String appnameParam) {
        HashMap<String, List<String>> appAddressMap = new HashMap<String, List<String>>();
        List<JobRegistry> list = jobRegistryDao.findAll(JobConstants.DEAD_TIMEOUT, new Date());
        if (list != null) {
            for (JobRegistry item : list) {
                if (RegistryType.EXECUTOR == item.getRegistryType()) {
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
    public Result<String> remove(Long id) {

        // valid
        int count = jobInfoDao.pageListCount(0, 10, id, -1, null, null, null);
        if (count > 0) {
            return new Result<String>(500, I18nUtil.getString("jobgroup_del_limit_0"));
        }

        List<JobGroup> allList = jobGroupDao.findAll();
        if (allList.size() == 1) {
            return new Result<String>(500, I18nUtil.getString("jobgroup_del_limit_1"));
        }

        int ret = jobGroupDao.remove(id);
        return (ret > 0) ? Result.SUCCESS : Result.FAIL;
    }

    @RequestMapping("/loadById")
    @ResponseBody
    public Result<JobGroup> loadById(Long id) {
        JobGroup jobGroup = jobGroupDao.load(id);
        return jobGroup != null ? new Result<JobGroup>(jobGroup) : new Result<JobGroup>(Result.FAIL_CODE, null);
    }

}
