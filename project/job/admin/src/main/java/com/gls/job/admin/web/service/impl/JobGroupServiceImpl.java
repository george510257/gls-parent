package com.gls.job.admin.web.service.impl;

import com.gls.job.admin.web.converter.JobGroupConverter;
import com.gls.job.admin.web.model.JobGroup;
import com.gls.job.admin.web.repository.JobGroupRepository;
import com.gls.job.admin.web.service.JobGroupService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author george
 */
@Service
public class JobGroupServiceImpl implements JobGroupService {

    @Resource
    private JobGroupConverter jobGroupConverter;

    @Resource
    private JobGroupRepository jobGroupRepository;

    @Override
    public List<JobGroup> getAllGroupList() {
        return jobGroupConverter.sourceToTargetList(jobGroupRepository.getAllList());
    }
}
