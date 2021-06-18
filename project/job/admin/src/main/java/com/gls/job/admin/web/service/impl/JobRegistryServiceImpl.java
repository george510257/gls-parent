package com.gls.job.admin.web.service.impl;

import com.gls.job.admin.web.dao.JobRegistryDao;
import com.gls.job.admin.web.entity.JobGroupEntity;
import com.gls.job.admin.web.entity.JobRegistryEntity;
import com.gls.job.admin.web.repository.JobGroupRepository;
import com.gls.job.admin.web.repository.JobRegistryRepository;
import com.gls.job.admin.web.service.JobRegistryService;
import com.gls.job.core.api.model.RegistryModel;
import com.gls.job.core.api.model.enums.RegistryType;
import com.gls.job.core.constants.JobConstants;
import com.gls.job.core.util.DateUtil;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author george
 */
@Service
public class JobRegistryServiceImpl implements JobRegistryService {
    @Resource
    private JobRegistryDao jobRegistryDao;

    @Resource
    private JobGroupRepository jobGroupRepository;
    @Resource
    private JobRegistryRepository jobRegistryRepository;

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

    @Override
    public void doJobRegistry() {
        List<JobGroupEntity> jobGroupEntities = jobGroupRepository.getByAddressTypeOrderByAppnameAscTitleAscIdAsc(true);
        if (ObjectUtils.isEmpty(jobGroupEntities)) {
            return;
        }
        Date updateDate = DateUtil.addSeconds(new Date(), -JobConstants.DEAD_TIMEOUT);
        List<JobRegistryEntity> jobRegistryEntities = jobRegistryRepository.getByUpdateDateBefore(updateDate);
        jobRegistryRepository.deleteAll(jobRegistryEntities);

        Map<String, List<String>> appAddressMap = new HashMap<>();
        jobRegistryRepository.getByUpdateDateAfter(updateDate).forEach(jobRegistryEntity -> {
            if (RegistryType.EXECUTOR.equals(jobRegistryEntity.getRegistryGroup())) {
                String appname = jobRegistryEntity.getRegistryKey();
                List<String> appAddressList = appAddressMap.computeIfAbsent(appname, v -> new ArrayList<>());
                if (!appAddressList.contains(jobRegistryEntity.getRegistryValue())) {
                    appAddressList.add(jobRegistryEntity.getRegistryValue());
                }
                appAddressMap.put(appname, appAddressList);
            }
        });

        jobGroupEntities.forEach(jobGroupEntity -> {
            List<String> registryList = appAddressMap.get(jobGroupEntity.getAppname());
            jobGroupEntity.setAddressList(registryList);
        });
        jobGroupRepository.saveAll(jobGroupEntities);
    }

    private void freshGroupRegistryInfo(RegistryModel registryModel) {
    }
}
