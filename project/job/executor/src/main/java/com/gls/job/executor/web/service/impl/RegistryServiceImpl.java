package com.gls.job.executor.web.service.impl;

import com.gls.job.core.api.model.RegistryModel;
import com.gls.job.core.api.model.Result;
import com.gls.job.core.api.model.enums.RegistryType;
import com.gls.job.core.api.rpc.AdminApi;
import com.gls.job.executor.web.service.RegistryService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author george
 */
@Slf4j
@Service
public class RegistryServiceImpl implements RegistryService {

    @Value("${spring.application.name}")
    private String appname;

    @Value("${}")
    private String address;

    @DubboReference
    private AdminApi adminApi;

    @Override
    public void registry() {
        RegistryModel registryModel = new RegistryModel(RegistryType.EXECUTOR, appname, address);
        Result<String> registryResult = adminApi.registry(registryModel);
        if (registryResult != null && Result.SUCCESS_CODE == registryResult.getCode()) {
            registryResult = Result.SUCCESS;
            log.debug(">>>>>>>>>>> gls-job registry success, registryModel:{}, registryResult:{}", registryModel, registryResult);
        } else {
            log.info(">>>>>>>>>>> gls-job registry fail, registryModel:{}, registryResult:{}", registryModel, registryResult);
        }
    }

    @Override
    public void registryRemove() {
        RegistryModel registryModel = new RegistryModel(RegistryType.EXECUTOR, appname, address);
        Result<String> registryResult = adminApi.registryRemove(registryModel);
        if (registryResult != null && Result.SUCCESS_CODE == registryResult.getCode()) {
            registryResult = Result.SUCCESS;
            log.info(">>>>>>>>>>> gls-job registry-remove success, registryModel:{}, registryResult:{}", registryModel, registryResult);
        } else {
            log.info(">>>>>>>>>>> gls-job registry-remove fail, registryModel:{}, registryResult:{}", registryModel, registryResult);
        }
        log.info(">>>>>>>>>>> gls-job, executor registry thread destroy.");
    }
}
