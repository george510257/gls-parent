package com.gls.job.admin.web.service.impl;

import com.gls.job.admin.web.converter.JobGroupConverter;
import com.gls.job.admin.web.entity.JobGroupEntity;
import com.gls.job.admin.web.entity.JobInfoEntity;
import com.gls.job.admin.web.model.JobGroup;
import com.gls.job.admin.web.repository.JobGroupRepository;
import com.gls.job.admin.web.repository.JobInfoRepository;
import com.gls.job.admin.web.service.JobGroupService;
import com.gls.job.admin.web.service.JobRegistryService;
import com.gls.job.core.exception.JobException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author george
 */
@Service("jobGroupService")
public class JobGroupServiceImpl implements JobGroupService {
    @Resource
    private JobGroupRepository jobGroupRepository;
    @Resource
    private JobGroupConverter jobGroupConverter;
    @Resource
    private JobRegistryService jobRegistryService;
    @Resource
    private JobInfoRepository jobInfoRepository;

    @Override
    public List<JobGroup> getAllList() {
        return jobGroupConverter.sourceToTargetList(jobGroupRepository.getAllList());
    }

    @Override
    public Map<String, Object> getPageList(String appname, String title, int start, int length) {
        Page<JobGroupEntity> jobGroupEntityPage = jobGroupRepository.getPage(appname, title, start, length);
        Map<String, Object> maps = new HashMap<>(3);
        // 总记录数
        maps.put("recordsTotal", jobGroupEntityPage.getTotalElements());
        // 过滤后的总记录数
        maps.put("recordsFiltered", jobGroupEntityPage.getTotalElements());
        // 分页列表
        maps.put("data", jobGroupConverter.sourceToTargetList(jobGroupEntityPage.getContent()));
        return maps;
    }

    @Override
    public void addJobGroup(JobGroup jobGroup) {
        if (ObjectUtils.isEmpty(jobGroup.getId())) {
            JobGroupEntity jobGroupEntity = jobGroupConverter.targetToSource(jobGroup);
            if (jobGroupEntity.getAddressType()) {
                jobGroupEntity.setAddressList(jobRegistryService.getAddressList(jobGroup.getAppname()));
            }
            jobGroupRepository.save(jobGroupEntity);
        }
    }

    @Override
    public void updateJobGroup(JobGroup jobGroup) {
        JobGroupEntity jobGroupEntity = jobGroupRepository.getOne(jobGroup.getId());
        if (!ObjectUtils.isEmpty(jobGroupEntity)) {
            jobGroupEntity = jobGroupConverter.copyTargetToSource(jobGroup, jobGroupEntity);
            if (jobGroupEntity.getAddressType()) {
                jobGroupEntity.setAddressList(jobRegistryService.getAddressList(jobGroup.getAppname()));
            }
            jobGroupRepository.save(jobGroupEntity);
        }
    }

    @Override
    public void removeJobGroup(Long id) {
        List<JobInfoEntity> jobInfoEntities = jobInfoRepository.getByJobGroupId(id);
        if (!ObjectUtils.isEmpty(jobInfoEntities)) {
            throw new JobException("拒绝删除，该执行器使用中");
        }
        long size = jobGroupRepository.count();
        if (size == 1) {
            throw new JobException("拒绝删除, 系统至少保留一个执行器");
        }
        jobGroupRepository.deleteById(id);
    }

    @Override
    public JobGroup loadJobGroupById(Long id) {
        JobGroupEntity jobGroupEntity = jobGroupRepository.getOne(id);
        if (ObjectUtils.isEmpty(jobGroupEntity)) {
            throw new JobException("执行器" + id + "不存在!");
        }
        return jobGroupConverter.sourceToTarget(jobGroupEntity);
    }
}
