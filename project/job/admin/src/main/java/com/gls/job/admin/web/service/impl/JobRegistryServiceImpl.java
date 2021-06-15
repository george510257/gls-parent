package com.gls.job.admin.web.service.impl;

import com.gls.job.admin.web.dao.JobRegistryDao;
import com.gls.job.admin.web.service.JobRegistryService;
import com.gls.job.core.api.model.RegistryModel;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author george
 */
@Service
public class JobRegistryServiceImpl implements JobRegistryService {
    @Resource
    private JobRegistryDao jobRegistryDao;

    @Async
    @Override
    public void registry(RegistryModel registryModel) {
        int ret = jobRegistryDao.registryUpdate(registryModel.getRegistryGroup(), registryModel.getRegistryKey(), registryModel.getRegistryValue(), new Date());
        if (ret < 1) {
            jobRegistryDao.registrySave(registryModel.getRegistryGroup(), registryModel.getRegistryKey(), registryModel.getRegistryValue(), new Date());
            // fresh
            freshGroupRegistryInfo(registryModel);
        }
    }

    @Async
    @Override
    public void registryRemove(RegistryModel registryModel) {
        int ret = jobRegistryDao.registryDelete(registryModel.getRegistryGroup(), registryModel.getRegistryKey(), registryModel.getRegistryValue());
        if (ret > 0) {
            // fresh
            freshGroupRegistryInfo(registryModel);
        }
    }

    private void freshGroupRegistryInfo(RegistryModel registryModel) {
    }
}
