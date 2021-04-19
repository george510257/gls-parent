package com.gls.job.executor.thread;

import com.gls.job.core.api.model.RegistryModel;
import com.gls.job.core.api.model.Result;
import com.gls.job.core.api.rpc.AdminApi;
import com.gls.job.core.enums.RegistryConfig;
import com.gls.job.executor.config.XxlJobExecutor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @author george
 */
@Slf4j
public class RegistryThread extends Thread {

    private final String appname;
    private final String address;
    @Setter
    private volatile boolean toStop = false;

    public RegistryThread(String appname, String address) {
        this.appname = appname;
        this.address = address;
    }

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
                            log.debug(">>>>>>>>>>> gls-job registry success, registryModel:{}, registryResult:{}", registryModel, registryResult);
                            break;
                        } else {
                            log.info(">>>>>>>>>>> gls-job registry fail, registryModel:{}, registryResult:{}", registryModel, registryResult);
                        }
                    } catch (Exception e) {
                        log.info(">>>>>>>>>>> gls-job registry error, registryModel:{}", registryModel, e);
                    }

                }
            } catch (Exception e) {
                if (!toStop) {
                    log.error(e.getMessage(), e);
                }

            }

            try {
                if (!toStop) {
                    TimeUnit.SECONDS.sleep(RegistryConfig.BEAT_TIMEOUT);
                }
            } catch (InterruptedException e) {
                if (!toStop) {
                    log.warn(">>>>>>>>>>> gls-job, executor registry thread interrupted, error msg:{}", e.getMessage());
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
                        log.info(">>>>>>>>>>> gls-job registry-remove success, registryModel:{}, registryResult:{}", registryModel, registryResult);
                        break;
                    } else {
                        log.info(">>>>>>>>>>> gls-job registry-remove fail, registryModel:{}, registryResult:{}", registryModel, registryResult);
                    }
                } catch (Exception e) {
                    if (!toStop) {
                        log.info(">>>>>>>>>>> gls-job registry-remove error, registryModel:{}", registryModel, e);
                    }

                }

            }
        } catch (Exception e) {
            if (!toStop) {
                log.error(e.getMessage(), e);
            }
        }
        log.info(">>>>>>>>>>> gls-job, executor registry thread destroy.");
    }
}
