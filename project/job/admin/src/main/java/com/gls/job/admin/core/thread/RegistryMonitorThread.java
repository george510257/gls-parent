package com.gls.job.admin.core.thread;

import com.gls.job.admin.core.conf.XxlJobAdminConfig;
import com.gls.job.admin.web.entity.XxlJobGroup;
import com.gls.job.admin.web.entity.XxlJobRegistry;
import com.gls.job.core.api.model.enums.RegistryType;
import com.gls.job.core.constants.JobConstants;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author george
 */
@Slf4j
public class RegistryMonitorThread extends Thread {

    @Setter
    private volatile boolean toStop = false;

    @Override
    public void run() {
        while (!toStop) {
            try {
                // auto registry group
                List<XxlJobGroup> groupList = XxlJobAdminConfig.getAdminConfig().getXxlJobGroupDao().findByAddressType(0);
                if (groupList != null && !groupList.isEmpty()) {

                    // remove dead address (admin/executor)
                    List<Integer> ids = XxlJobAdminConfig.getAdminConfig().getXxlJobRegistryDao().findDead(JobConstants.DEAD_TIMEOUT, new Date());
                    if (ids != null && ids.size() > 0) {
                        XxlJobAdminConfig.getAdminConfig().getXxlJobRegistryDao().removeDead(ids);
                    }

                    // fresh online address (admin/executor)
                    HashMap<String, List<String>> appAddressMap = new HashMap<String, List<String>>();
                    List<XxlJobRegistry> list = XxlJobAdminConfig.getAdminConfig().getXxlJobRegistryDao().findAll(JobConstants.DEAD_TIMEOUT, new Date());
                    if (list != null) {
                        for (XxlJobRegistry item : list) {
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

                    // fresh group address
                    for (XxlJobGroup group : groupList) {
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

                        XxlJobAdminConfig.getAdminConfig().getXxlJobGroupDao().update(group);
                    }
                }
            } catch (Exception e) {
                if (!toStop) {
                    log.error(">>>>>>>>>>> gls-job, job registry monitor thread error:{}", e);
                }
            }
            try {
                TimeUnit.SECONDS.sleep(JobConstants.BEAT_TIMEOUT);
            } catch (InterruptedException e) {
                if (!toStop) {
                    log.error(">>>>>>>>>>> gls-job, job registry monitor thread error:{}", e);
                }
            }
        }
        log.info(">>>>>>>>>>> gls-job, job registry monitor thread stop");
    }
}
