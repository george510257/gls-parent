package com.xxl.job.admin.core.thread;

import com.gls.job.admin.web.dao.JobGroupDao;
import com.gls.job.admin.web.dao.JobRegistryDao;
import com.gls.job.admin.web.model.JobGroup;
import com.gls.job.admin.web.model.JobRegistry;
import com.gls.job.core.api.model.RegistryModel;
import com.gls.job.core.api.model.Result;
import com.gls.job.core.api.model.enums.RegistryType;
import com.gls.job.core.constants.JobConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * job registry instance
 *
 * @author xuxueli 2016-10-02 19:10:24
 */
@Slf4j
public class JobRegistryHelper {
    private static JobRegistryHelper instance = new JobRegistryHelper();
    private ThreadPoolExecutor registryOrRemoveThreadPool = null;
    private Thread registryMonitorThread;
    private volatile boolean toStop = false;
    @Resource
    private JobRegistryDao jobRegistryDao;
    @Resource
    private JobGroupDao jobGroupDao;

    public static JobRegistryHelper getInstance() {
        return instance;
    }

    public void start() {

        // for registry or remove
        registryOrRemoveThreadPool = new ThreadPoolExecutor(
                2,
                10,
                30L,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(2000),
                r -> new Thread(r, "gls-job, admin JobRegistryMonitorHelper-registryOrRemoveThreadPool-" + r.hashCode()),
                (r, executor) -> {
                    r.run();
                    log.warn(">>>>>>>>>>> gls-job, registry or remove too fast, match threadpool rejected handler(run now).");
                });

        // for monitor
        registryMonitorThread = new Thread(() -> {
            while (!toStop) {
                try {
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
        });
        registryMonitorThread.setDaemon(true);
        registryMonitorThread.setName("gls-job, admin JobRegistryMonitorHelper-registryMonitorThread");
        registryMonitorThread.start();
    }

    public void toStop() {
        toStop = true;

        // stop registryOrRemoveThreadPool
        registryOrRemoveThreadPool.shutdownNow();

        // stop monitir (interrupt and wait)
        registryMonitorThread.interrupt();
        try {
            registryMonitorThread.join();
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
        }
    }

    // ---------------------- helper ----------------------

    public Result<String> registry(RegistryModel registryModel) {

        // valid
        if (ObjectUtils.isEmpty(registryModel.getRegistryGroup())
                || !StringUtils.hasText(registryModel.getRegistryKey())
                || !StringUtils.hasText(registryModel.getRegistryValue())) {
            return new Result<>(Result.FAIL_CODE, "Illegal Argument.");
        }

        // async execute
        registryOrRemoveThreadPool.execute(() -> {
            int ret = jobRegistryDao.registryUpdate(registryModel.getRegistryGroup(), registryModel.getRegistryKey(), registryModel.getRegistryValue(), new Date());
            if (ret < 1) {
                jobRegistryDao.registrySave(registryModel.getRegistryGroup(), registryModel.getRegistryKey(), registryModel.getRegistryValue(), new Date());

                // fresh
                freshGroupRegistryInfo(registryModel);
            }
        });

        return Result.SUCCESS;
    }

    public Result<String> registryRemove(RegistryModel registryModel) {

        // valid
        if (ObjectUtils.isEmpty(registryModel.getRegistryGroup())
                || !StringUtils.hasText(registryModel.getRegistryKey())
                || !StringUtils.hasText(registryModel.getRegistryValue())) {
            return new Result<>(Result.FAIL_CODE, "Illegal Argument.");
        }

        // async execute
        registryOrRemoveThreadPool.execute(() -> {
            int ret = jobRegistryDao.registryDelete(registryModel.getRegistryGroup(), registryModel.getRegistryKey(), registryModel.getRegistryValue());
            if (ret > 0) {
                // fresh
                freshGroupRegistryInfo(registryModel);
            }
        });

        return Result.SUCCESS;
    }

    private void freshGroupRegistryInfo(RegistryModel registryModel) {
        // Under consideration, prevent affecting core tables
    }

}
