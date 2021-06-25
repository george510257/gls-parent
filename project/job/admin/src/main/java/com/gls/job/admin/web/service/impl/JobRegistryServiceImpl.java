package com.gls.job.admin.web.service.impl;

import com.gls.job.admin.web.entity.JobGroupEntity;
import com.gls.job.admin.web.entity.JobRegistryEntity;
import com.gls.job.admin.web.repository.JobGroupRepository;
import com.gls.job.admin.web.repository.JobRegistryRepository;
import com.gls.job.admin.web.service.JobRegistryService;
import com.gls.job.core.api.model.RegistryModel;
import com.gls.job.core.api.model.enums.RegistryType;
import com.gls.job.core.constants.JobConstants;
import com.gls.job.core.util.DateUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.*;

/**
 * @author george
 */
@Service("jobRegistryService")
public class JobRegistryServiceImpl implements JobRegistryService {

    @Resource
    private JobRegistryRepository jobRegistryRepository;
    @Resource
    private JobGroupRepository jobGroupRepository;

    @Override
    public void registry(RegistryModel registryModel) {
        JobRegistryEntity jobRegistryEntity = jobRegistryRepository.getByRegistryGroupAndRegistryKeyAndRegistryValue(registryModel.getRegistryGroup(), registryModel.getRegistryKey(), registryModel.getRegistryValue());
        if (ObjectUtils.isEmpty(jobRegistryEntity)) {
            jobRegistryEntity = new JobRegistryEntity();
            jobRegistryEntity.setRegistryGroup(registryModel.getRegistryGroup());
            jobRegistryEntity.setRegistryKey(registryModel.getRegistryKey());
            jobRegistryEntity.setRegistryValue(registryModel.getRegistryValue());
        }
        jobRegistryEntity.setUpdateDate(new Timestamp(System.currentTimeMillis()));
        jobRegistryRepository.save(jobRegistryEntity);
    }

    @Override
    public void registryRemove(RegistryModel registryModel) {
        JobRegistryEntity jobRegistryEntity = jobRegistryRepository.getByRegistryGroupAndRegistryKeyAndRegistryValue(registryModel.getRegistryGroup(), registryModel.getRegistryKey(), registryModel.getRegistryValue());
        if (!ObjectUtils.isEmpty(jobRegistryEntity)) {
            jobRegistryRepository.delete(jobRegistryEntity);
        }
    }

    @Override
    public void doJobRegistry() {
        List<JobGroupEntity> jobGroupEntities = jobGroupRepository.getByAddressTypeOrderByAppnameAscTitleAscIdAsc(true);
        if (ObjectUtils.isEmpty(jobGroupEntities)) {
            return;
        }
        Date date = DateUtil.addSeconds(new Date(), -JobConstants.DEAD_TIMEOUT);
        // remove dead address (admin/executor)
        removeOld(date);

        // fresh online address (admin/executor)
        Map<String, List<String>> appAddressMap = freshOnline(date);

        jobGroupEntities.forEach(jobGroupEntity -> {
            List<String> registryList = appAddressMap.get(jobGroupEntity.getAppname());
            jobGroupEntity.setAddressList(registryList);
            jobGroupRepository.save(jobGroupEntity);
        });
    }

    @Override
    public List<String> getAddressList(String appname) {
        return freshOnline(DateUtil.addSeconds(new Date(), -JobConstants.DEAD_TIMEOUT)).get(appname);
    }

    private Map<String, List<String>> freshOnline(Date date) {
        Map<String, List<String>> appAddressMap = new HashMap<>();
        List<JobRegistryEntity> jobRegistryEntities = jobRegistryRepository.getByUpdateDateAfter(date);
        if (!ObjectUtils.isEmpty(jobRegistryEntities)) {
            jobRegistryEntities.forEach(jobRegistryEntity -> {
                if (RegistryType.EXECUTOR.equals(jobRegistryEntity.getRegistryGroup())) {
                    String appname = jobRegistryEntity.getRegistryKey();
                    List<String> registryList = appAddressMap.getOrDefault(appname, new ArrayList<>());

                    if (!registryList.contains(jobRegistryEntity.getRegistryValue())) {
                        registryList.add(jobRegistryEntity.getRegistryValue());
                    }
                    appAddressMap.put(appname, registryList);
                }
            });
        }
        return appAddressMap;
    }

    private void removeOld(Date date) {
        List<JobRegistryEntity> jobRegistryEntities = jobRegistryRepository.getByUpdateDateBefore(date);
        if (!ObjectUtils.isEmpty(jobRegistryEntities)) {
            jobRegistryRepository.deleteAll(jobRegistryEntities);
        }
    }
}
