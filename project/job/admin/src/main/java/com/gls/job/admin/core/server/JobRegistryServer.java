package com.gls.job.admin.core.server;

import com.gls.job.admin.core.conf.JobAdminConfig;
import com.gls.job.admin.core.thread.RegistryMonitorThread;
import com.gls.job.core.api.model.RegistryModel;
import com.gls.job.core.api.model.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * job registry instance
 *
 * @author george 2016-10-02 19:10:24
 */
@Slf4j
public class JobRegistryServer {

    private static final JobRegistryServer INSTANCE = new JobRegistryServer();
    private ThreadPoolExecutor registryOrRemoveThreadPool = null;
    private RegistryMonitorThread registryMonitorThread;

    public static JobRegistryServer getInstance() {
        return INSTANCE;
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
        registryMonitorThread = new RegistryMonitorThread();
        registryMonitorThread.setDaemon(true);
        registryMonitorThread.setName("gls-job, admin JobRegistryMonitorHelper-registryMonitorThread");
        registryMonitorThread.start();
    }

    public void toStop() {
        registryMonitorThread.toStop("");

        // stop registryOrRemoveThreadPool
        registryOrRemoveThreadPool.shutdownNow();
    }

    // ---------------------- helper ----------------------

    public Result<String> registry(RegistryModel registryModel) {

        // valid
        if (registryModel.getRegistryType() == null
                || !StringUtils.hasText(registryModel.getRegistryKey())
                || !StringUtils.hasText(registryModel.getRegistryValue())) {
            return new Result<>(Result.FAIL_CODE, "Illegal Argument.");
        }

        // async execute
        registryOrRemoveThreadPool.execute(() -> {
            int ret = JobAdminConfig.getAdminConfig().getJobRegistryDao().registryUpdate(registryModel.getRegistryType(), registryModel.getRegistryKey(), registryModel.getRegistryValue(), new Date());
            if (ret < 1) {
                JobAdminConfig.getAdminConfig().getJobRegistryDao().registrySave(registryModel.getRegistryType(), registryModel.getRegistryKey(), registryModel.getRegistryValue(), new Date());

                // fresh
                freshGroupRegistryInfo(registryModel);
            }
        });

        return Result.SUCCESS;
    }

    public Result<String> registryRemove(RegistryModel registryModel) {

        // valid
        if (registryModel.getRegistryType() == null
                || !StringUtils.hasText(registryModel.getRegistryKey())
                || !StringUtils.hasText(registryModel.getRegistryValue())) {
            return new Result<>(Result.FAIL_CODE, "Illegal Argument.");
        }

        // async execute
        registryOrRemoveThreadPool.execute(() -> {
            int ret = JobAdminConfig.getAdminConfig().getJobRegistryDao().registryDelete(registryModel.getRegistryType(), registryModel.getRegistryKey(), registryModel.getRegistryValue());
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
