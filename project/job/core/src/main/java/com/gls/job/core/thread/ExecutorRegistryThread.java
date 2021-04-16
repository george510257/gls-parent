package com.gls.job.core.thread;

import com.gls.job.core.api.model.RegistryModel;
import com.gls.job.core.api.model.Result;
import com.gls.job.core.api.rpc.AdminApi;
import com.gls.job.core.enums.RegistryConfig;
import com.gls.job.core.executor.XxlJobExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * Created by george on 17/3/2.
 */
public class ExecutorRegistryThread {
    private static Logger logger = LoggerFactory.getLogger(ExecutorRegistryThread.class);

    private static ExecutorRegistryThread instance = new ExecutorRegistryThread();
    private Thread registryThread;
    private volatile boolean toStop = false;

    public static ExecutorRegistryThread getInstance() {
        return instance;
    }

    public void start(final String appname, final String address) {

        // valid
        if (appname == null || appname.trim().length() == 0) {
            logger.warn(">>>>>>>>>>> gls-job, executor registry config fail, appname is null.");
            return;
        }
        if (XxlJobExecutor.getAdminApiList() == null) {
            logger.warn(">>>>>>>>>>> gls-job, executor registry config fail, adminAddresses is null.");
            return;
        }

        registryThread = new Thread(new Runnable() {
            @Override
            public void run() {

                // registry
                while (!toStop) {
                    try {
                        RegistryModel registryModel = new RegistryModel(RegistryConfig.RegistType.EXECUTOR.name(), appname, address);
                        for (AdminApi adminApi : XxlJobExecutor.getAdminApiList()) {
                            try {
                                Result<String> registryResult = adminApi.registry(registryModel);
                                if (registryResult != null && Result.SUCCESS_CODE == registryResult.getCode()) {
                                    registryResult = Result.SUCCESS;
                                    logger.debug(">>>>>>>>>>> gls-job registry success, registryModel:{}, registryResult:{}", new Object[]{registryModel, registryResult});
                                    break;
                                } else {
                                    logger.info(">>>>>>>>>>> gls-job registry fail, registryModel:{}, registryResult:{}", new Object[]{registryModel, registryResult});
                                }
                            } catch (Exception e) {
                                logger.info(">>>>>>>>>>> gls-job registry error, registryModel:{}", registryModel, e);
                            }

                        }
                    } catch (Exception e) {
                        if (!toStop) {
                            logger.error(e.getMessage(), e);
                        }

                    }

                    try {
                        if (!toStop) {
                            TimeUnit.SECONDS.sleep(RegistryConfig.BEAT_TIMEOUT);
                        }
                    } catch (InterruptedException e) {
                        if (!toStop) {
                            logger.warn(">>>>>>>>>>> gls-job, executor registry thread interrupted, error msg:{}", e.getMessage());
                        }
                    }
                }

                // registry remove
                try {
                    RegistryModel registryModel = new RegistryModel(RegistryConfig.RegistType.EXECUTOR.name(), appname, address);
                    for (AdminApi adminApi : XxlJobExecutor.getAdminApiList()) {
                        try {
                            Result<String> registryResult = adminApi.registryRemove(registryModel);
                            if (registryResult != null && Result.SUCCESS_CODE == registryResult.getCode()) {
                                registryResult = Result.SUCCESS;
                                logger.info(">>>>>>>>>>> gls-job registry-remove success, registryModel:{}, registryResult:{}", new Object[]{registryModel, registryResult});
                                break;
                            } else {
                                logger.info(">>>>>>>>>>> gls-job registry-remove fail, registryModel:{}, registryResult:{}", new Object[]{registryModel, registryResult});
                            }
                        } catch (Exception e) {
                            if (!toStop) {
                                logger.info(">>>>>>>>>>> gls-job registry-remove error, registryModel:{}", registryModel, e);
                            }

                        }

                    }
                } catch (Exception e) {
                    if (!toStop) {
                        logger.error(e.getMessage(), e);
                    }
                }
                logger.info(">>>>>>>>>>> gls-job, executor registry thread destory.");

            }
        });
        registryThread.setDaemon(true);
        registryThread.setName("gls-job, executor ExecutorRegistryThread");
        registryThread.start();
    }

    public void toStop() {
        toStop = true;

        // interrupt and wait
        if (registryThread != null) {
            registryThread.interrupt();
            try {
                registryThread.join();
            } catch (InterruptedException e) {
                logger.error(e.getMessage(), e);
            }
        }

    }

}
