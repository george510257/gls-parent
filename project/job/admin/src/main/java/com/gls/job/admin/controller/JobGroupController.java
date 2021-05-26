package com.gls.job.admin.controller;

import com.gls.job.admin.core.model.JobGroup;
import com.gls.job.admin.core.model.JobRegistry;
import com.gls.job.admin.core.util.I18nUtil;
import com.gls.job.admin.dao.JobGroupDao;
import com.gls.job.admin.dao.JobInfoDao;
import com.gls.job.admin.dao.JobRegistryDao;
import com.gls.job.core.biz.model.ReturnT;
import com.gls.job.core.enums.RegistryConfig;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.*;

/**
 * job group controller
 *
 * @author xuxueli 2016-10-02 20:52:56
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
    public String index() {
        return "jobgroup/jobgroup.index";
    }

    @RequestMapping("/pageList")
    @ResponseBody
    public Map<String, Object> pageList(@RequestParam(required = false, defaultValue = "0") int start,
                                        @RequestParam(required = false, defaultValue = "10") int length,
                                        String appname, String title) {

        // page query
        List<JobGroup> list = jobGroupDao.pageList(start, length, appname, title);
        int listCount = jobGroupDao.pageListCount(start, length, appname, title);

        // package result
        Map<String, Object> maps = new HashMap<>();
        maps.put("recordsTotal", listCount);        // 总记录数
        maps.put("recordsFiltered", listCount);    // 过滤后的总记录数
        maps.put("data", list);                    // 分页列表
        return maps;
    }

    @RequestMapping("/save")
    @ResponseBody
    public ReturnT<String> save(JobGroup jobGroup) {

        // valid
        if (jobGroup.getAppname() == null || jobGroup.getAppname().trim().length() == 0) {
            return new ReturnT<>(500, (I18nUtil.getString("system_please_input") + "AppName"));
        }
        if (jobGroup.getAppname().length() < 4 || jobGroup.getAppname().length() > 64) {
            return new ReturnT<>(500, I18nUtil.getString("job_group_field_appname_length"));
        }
        if (jobGroup.getAppname().contains(">") || jobGroup.getAppname().contains("<")) {
            return new ReturnT<>(500, "AppName" + I18nUtil.getString("system_un_valid"));
        }
        if (jobGroup.getTitle() == null || jobGroup.getTitle().trim().length() == 0) {
            return new ReturnT<>(500, (I18nUtil.getString("system_please_input") + I18nUtil.getString("job_group_field_title")));
        }
        if (jobGroup.getTitle().contains(">") || jobGroup.getTitle().contains("<")) {
            return new ReturnT<>(500, I18nUtil.getString("job_group_field_title") + I18nUtil.getString("system_un_valid"));
        }
        if (jobGroup.getAddressType() != 0) {
            if (jobGroup.getAddressList() == null || jobGroup.getAddressList().trim().length() == 0) {
                return new ReturnT<>(500, I18nUtil.getString("job_group_field_addressType_limit"));
            }
            if (jobGroup.getAddressList().contains(">") || jobGroup.getAddressList().contains("<")) {
                return new ReturnT<>(500, I18nUtil.getString("job_group_field_registryList") + I18nUtil.getString("system_un_valid"));
            }

            String[] addresses = jobGroup.getAddressList().split(",");
            for (String item : addresses) {
                if (item == null || item.trim().length() == 0) {
                    return new ReturnT<>(500, I18nUtil.getString("job_group_field_registryList_un_valid"));
                }
            }
        }

        // process
        jobGroup.setUpdateTime(new Date());

        int ret = jobGroupDao.save(jobGroup);
        return (ret > 0) ? ReturnT.SUCCESS : ReturnT.FAIL;
    }

    @RequestMapping("/update")
    @ResponseBody
    public ReturnT<String> update(JobGroup jobGroup) {
        // valid
        if (jobGroup.getAppname() == null || jobGroup.getAppname().trim().length() == 0) {
            return new ReturnT<>(500, (I18nUtil.getString("system_please_input") + "AppName"));
        }
        if (jobGroup.getAppname().length() < 4 || jobGroup.getAppname().length() > 64) {
            return new ReturnT<>(500, I18nUtil.getString("job_group_field_appname_length"));
        }
        if (jobGroup.getTitle() == null || jobGroup.getTitle().trim().length() == 0) {
            return new ReturnT<>(500, (I18nUtil.getString("system_please_input") + I18nUtil.getString("job_group_field_title")));
        }
        if (jobGroup.getAddressType() == 0) {
            // 0=自动注册
            List<String> registryList = findRegistryByAppName(jobGroup.getAppname());
            StringBuilder addressListStr = null;
            if (registryList != null && !registryList.isEmpty()) {
                Collections.sort(registryList);
                addressListStr = new StringBuilder();
                for (String item : registryList) {
                    addressListStr.append(item).append(",");
                }
                addressListStr = new StringBuilder(addressListStr.substring(0, addressListStr.length() - 1));
            }
            jobGroup.setAddressList(addressListStr.toString());
        } else {
            // 1=手动录入
            if (jobGroup.getAddressList() == null || jobGroup.getAddressList().trim().length() == 0) {
                return new ReturnT<>(500, I18nUtil.getString("job_group_field_addressType_limit"));
            }
            String[] addresses = jobGroup.getAddressList().split(",");
            for (String item : addresses) {
                if (item == null || item.trim().length() == 0) {
                    return new ReturnT<>(500, I18nUtil.getString("job_group_field_registryList_un_valid"));
                }
            }
        }

        // process
        jobGroup.setUpdateTime(new Date());

        int ret = jobGroupDao.update(jobGroup);
        return (ret > 0) ? ReturnT.SUCCESS : ReturnT.FAIL;
    }

    private List<String> findRegistryByAppName(String appnameParam) {
        HashMap<String, List<String>> appAddressMap = new HashMap<>();
        List<JobRegistry> list = jobRegistryDao.findAll(RegistryConfig.DEAD_TIMEOUT, new Date());
        if (list != null) {
            for (JobRegistry item : list) {
                if (RegistryConfig.RegistType.EXECUTOR.name().equals(item.getRegistryGroup())) {
                    String appname = item.getRegistryKey();
                    List<String> registryList = appAddressMap.get(appname);
                    if (registryList == null) {
                        registryList = new ArrayList<>();
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
    public ReturnT<String> remove(int id) {

        // valid
        int count = jobInfoDao.pageListCount(0, 10, id, -1, null, null, null);
        if (count > 0) {
            return new ReturnT<>(500, I18nUtil.getString("job_group_del_limit_0"));
        }

        List<JobGroup> allList = jobGroupDao.findAll();
        if (allList.size() == 1) {
            return new ReturnT<>(500, I18nUtil.getString("job_group_del_limit_1"));
        }

        int ret = jobGroupDao.remove(id);
        return (ret > 0) ? ReturnT.SUCCESS : ReturnT.FAIL;
    }

    @RequestMapping("/loadById")
    @ResponseBody
    public ReturnT<JobGroup> loadById(int id) {
        JobGroup jobGroup = jobGroupDao.load(id);
        return jobGroup != null ? new ReturnT<>(jobGroup) : new ReturnT<>(ReturnT.FAIL_CODE, null);
    }

}
