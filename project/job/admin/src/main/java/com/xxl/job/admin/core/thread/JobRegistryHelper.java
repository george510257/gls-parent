package com.xxl.job.admin.core.thread;

import com.gls.job.admin.web.dao.JobGroupDao;
import com.gls.job.admin.web.dao.JobRegistryDao;
import com.gls.job.core.api.model.RegistryModel;
import com.gls.job.core.api.model.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Date;
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
