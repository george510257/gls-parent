package com.xxl.job.core.thread;

import com.gls.job.core.api.model.RegistryModel;
import com.gls.job.core.api.model.Result;
import com.gls.job.core.api.model.enums.RegistryType;
import com.gls.job.core.api.rpc.AdminApi;
import com.xxl.job.core.enums.RegistryConfig;
import com.xxl.job.core.executor.XxlJobExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * @author xuxueli
 * @date 17/3/2
 */
public class ExecutorRegistryThread {
    private static final Logger logger = LoggerFactory.getLogger(ExecutorRegistryThread.class);

    private static final ExecutorRegistryThread instance = new ExecutorRegistryThread();
    private Thread registryThread;
    private volatile boolean toStop = false;

    public static ExecutorRegistryThread getInstance() {
        return instance;
    }

    public void start(final String appname, final String address) {

        // valid
        if (appname == null || appname.trim().length() == 0) {
            logger.warn(">>>>>>>>>>> xxl-job, executor registry config fail, appname is null.");
            return;
        }
        if (XxlJobExecutor.getAdminBizList() == null) {
            logger.warn(">>>>>>>>>>> xxl-job, executor registry config fail, adminAddresses is null.");
            return;
        }

        registryThread = new Thread(new Runnable() {
            @Override
            public void run() {

                // registry
                while (!toStop) {
                    try {
                        RegistryModel registryModel = new RegistryModel(RegistryType.EXECUTOR, appname, address);
                        for (AdminApi adminApi : XxlJobExecutor.getAdminBizList()) {
                            try {
                                Result<String> registryResult = adminApi.registry(registryModel);
                                if (registryResult != null && Result.SUCCESS_CODE == registryResult.getCode()) {
                                    registryResult = Result.SUCCESS;
                                    logger.debug(">>>>>>>>>>> xxl-job registry success, registryModel:{}, registryResult:{}", new Object[]{registryModel, registryResult});
                                    break;
                                } else {
                                    logger.info(">>>>>>>>>>> xxl-job registry fail, registryModel:{}, registryResult:{}", new Object[]{registryModel, registryResult});
                                }
                            } catch (Exception e) {
                                logger.info(">>>>>>>>>>> xxl-job registry error, registryModel:{}", registryModel, e);
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
                            logger.warn(">>>>>>>>>>> xxl-job, executor registry thread interrupted, error msg:{}", e.getMessage());
                        }
                    }
                }

                // registry remove
                try {
                    RegistryModel registryModel = new RegistryModel(RegistryType.EXECUTOR, appname, address);
                    for (AdminApi adminApi : XxlJobExecutor.getAdminBizList()) {
                        try {
                            Result<String> registryResult = adminApi.registryRemove(registryModel);
                            if (registryResult != null && Result.SUCCESS_CODE == registryResult.getCode()) {
                                registryResult = Result.SUCCESS;
                                logger.info(">>>>>>>>>>> xxl-job registry-remove success, registryModel:{}, registryResult:{}", new Object[]{registryModel, registryResult});
                                break;
                            } else {
                                logger.info(">>>>>>>>>>> xxl-job registry-remove fail, registryModel:{}, registryResult:{}", new Object[]{registryModel, registryResult});
                            }
                        } catch (Exception e) {
                            if (!toStop) {
                                logger.info(">>>>>>>>>>> xxl-job registry-remove error, registryModel:{}", registryModel, e);
                            }

                        }

                    }
                } catch (Exception e) {
                    if (!toStop) {
                        logger.error(e.getMessage(), e);
                    }
                }
                logger.info(">>>>>>>>>>> xxl-job, executor registry thread destory.");

            }
        });
        registryThread.setDaemon(true);
        registryThread.setName("xxl-job, executor ExecutorRegistryThread");
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
