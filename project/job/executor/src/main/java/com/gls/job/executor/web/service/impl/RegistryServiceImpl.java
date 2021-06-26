package com.gls.job.executor.web.service.impl;

import com.gls.framework.api.result.Result;
import com.gls.job.core.api.model.RegistryModel;
import com.gls.job.core.api.rpc.AdminApi;
import com.gls.job.core.constants.RegistryType;
import com.gls.job.executor.core.constants.JobExecutorProperties;
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
    private JobExecutorProperties jobExecutorProperties;
    @DubboReference
    private AdminApi adminApi;

    @Override
    public void registry() {
        RegistryModel registryModel = new RegistryModel(RegistryType.EXECUTOR, jobExecutorProperties.getAppname(), jobExecutorProperties.getAddress());
//        for (AdminApi adminApi : JobExecutor.getAdminBizList()) {
        try {
            Result<String> registryResult = adminApi.registry(registryModel);
            if (registryResult != null && Result.SUCCESS_CODE.equals(registryResult.getCode())) {
                registryResult = Result.SUCCESS;
                log.debug(">>>>>>>>>>> gls-job registry success, registryModel:{}, registryResult:{}", registryModel, registryResult);
//                    break;
            } else {
                log.info(">>>>>>>>>>> gls-job registry fail, registryModel:{}, registryResult:{}", registryModel, registryResult);
            }
        } catch (Exception e) {
            log.info(">>>>>>>>>>> gls-job registry error, registryModel:{}", registryModel, e);
        }
//        }
    }

    @Override
    public void registryRemove() {
        RegistryModel registryModel = new RegistryModel(RegistryType.EXECUTOR, jobExecutorProperties.getAppname(), jobExecutorProperties.getAddress());
//        for (AdminApi adminApi : JobExecutor.getAdminBizList()) {
        try {
            Result<String> registryResult = adminApi.registryRemove(registryModel);
            if (registryResult != null && Result.SUCCESS_CODE.equals(registryResult.getCode())) {
                registryResult = Result.SUCCESS;
                log.info(">>>>>>>>>>> gls-job registry-remove success, registryModel:{}, registryResult:{}", registryModel, registryResult);
//                    break;
            } else {
                log.info(">>>>>>>>>>> gls-job registry-remove fail, registryModel:{}, registryResult:{}", registryModel, registryResult);
            }
        } catch (Exception e) {
            log.info(">>>>>>>>>>> gls-job registry-remove error, registryModel:{}", registryModel, e);
        }
//        }
    }
}
