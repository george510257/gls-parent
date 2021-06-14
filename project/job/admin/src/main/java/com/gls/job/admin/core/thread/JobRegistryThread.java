package com.gls.job.admin.core.thread;

import com.gls.job.admin.web.dao.JobGroupDao;
import com.gls.job.admin.web.dao.JobRegistryDao;
import com.gls.job.admin.web.model.JobGroup;
import com.gls.job.admin.web.model.JobRegistry;
import com.gls.job.core.api.model.enums.RegistryType;
import com.gls.job.core.base.BaseThread;
import com.gls.job.core.constants.JobConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author george
 */
@Slf4j
@Component
public class JobRegistryThread extends BaseThread {

    @Resource
    private JobRegistryDao jobRegistryDao;
    @Resource
    private JobGroupDao jobGroupDao;

    @Override
    protected void initExecute() throws Exception {
        log.info(">>>>>>>>>>> gls-job, JobRegistryThread init");
    }

    @Override
    protected void doExecute() throws Exception {
        // auto registry group
        List<JobGroup> groupList = jobGroupDao.findByAddressType(0);
        if (groupList != null && !groupList.isEmpty()) {

            // remove dead address (admin/executor)
            List<Integer> ids = jobRegistryDao.findDead(JobConstants.DEAD_TIMEOUT, new Date());
            if (ids != null && ids.size() > 0) {
                jobRegistryDao.removeDead(ids);
            }

            // fresh online address (admin/executor)
            HashMap<String, List<String>> appAddressMap = new HashMap<>();
            List<JobRegistry> list = jobRegistryDao.findAll(JobConstants.DEAD_TIMEOUT, new Date());
            if (list != null) {
                for (JobRegistry item : list) {
                    if (RegistryType.EXECUTOR.name().equals(item.getRegistryGroup())) {
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

            // fresh group address
            for (JobGroup group : groupList) {
                List<String> registryList = appAddressMap.get(group.getAppname());
                String addressListStr = null;
                if (registryList != null && !registryList.isEmpty()) {
                    Collections.sort(registryList);
                    StringBuilder addressListSB = new StringBuilder();
                    for (String item : registryList) {
                        addressListSB.append(item).append(",");
                    }
                    addressListStr = addressListSB.toString();
                    addressListStr = addressListStr.substring(0, addressListStr.length() - 1);
                }
                group.setAddressList(addressListStr);
                group.setUpdateTime(new Date());

                jobGroupDao.update(group);
            }
        }
    }

    @Override
    protected void sleepExecute() throws Exception {
        TimeUnit.SECONDS.sleep(JobConstants.BEAT_TIMEOUT);
    }

    @Override
    protected void destroyExecute() throws Exception {
        log.info(">>>>>>>>>>> gls-job, JobRegistryThread destroy");
    }
}
