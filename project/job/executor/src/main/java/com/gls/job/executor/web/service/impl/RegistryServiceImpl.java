package com.gls.job.executor.web.service.impl;

import com.gls.job.core.api.model.RegistryModel;
import com.gls.job.core.api.model.Result;
import com.gls.job.core.api.model.enums.RegistryType;
import com.gls.job.core.api.rpc.AdminApi;
import com.gls.job.executor.core.constants.ExecutorProperties;
import com.gls.job.executor.web.service.RegistryService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author george
 */
@Slf4j
@Service
public class RegistryServiceImpl implements RegistryService {

    @Resource
    private ExecutorProperties executorProperties;

    @DubboReference
    private AdminApi adminApi;

    @Override
    public void registry() {
        RegistryModel registryModel = new RegistryModel(RegistryType.EXECUTOR, executorProperties.getAppname(), executorProperties.getAddress());
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
        RegistryModel registryModel = new RegistryModel(RegistryType.EXECUTOR, executorProperties.getAppname(), executorProperties.getAddress());
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
