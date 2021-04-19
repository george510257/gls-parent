package com.gls.job.executor.server;

import com.gls.job.executor.config.XxlJobExecutor;
import com.gls.job.executor.thread.RegistryThread;
import lombok.extern.slf4j.Slf4j;

/**
 * @author george
 */
@Slf4j
public class RegistryServer {

    private static final RegistryServer INSTANCE = new RegistryServer();
    private RegistryThread registryThread;

    public static RegistryServer getInstance() {
        return INSTANCE;
    }

    public void start(final String appname, final String address) {

        // valid
        if (appname == null || appname.trim().length() == 0) {
            log.warn(">>>>>>>>>>> gls-job, executor registry config fail, appname is null.");
            return;
        }
        if (XxlJobExecutor.getAdminApiList() == null) {
            log.warn(">>>>>>>>>>> gls-job, executor registry config fail, adminAddresses is null.");
            return;
        }

        registryThread = new RegistryThread(appname, address);
        registryThread.setDaemon(true);
        registryThread.setName("gls-job, executor ExecutorRegistryThread");
        registryThread.start();
    }

    public void toStop() {
        registryThread.setToStop(true);

        // interrupt and wait
        if (registryThread != null) {
            registryThread.interrupt();
            try {
                registryThread.join();
            } catch (InterruptedException e) {
                log.error(e.getMessage(), e);
            }
        }

    }

}
