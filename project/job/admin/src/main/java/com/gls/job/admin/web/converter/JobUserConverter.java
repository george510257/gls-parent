package com.gls.job.admin.web.converter;

import com.gls.framework.core.base.BaseConverter;
import com.gls.framework.core.util.StringUtil;
import com.gls.job.admin.web.entity.JobUserEntity;
import com.gls.job.admin.web.model.JobUser;
import com.gls.job.admin.web.repository.JobGroupRepository;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

/**
 * @author george
 */
@Component
public class JobUserConverter implements BaseConverter<JobUserEntity, JobUser> {
    @Resource
    private JobGroupRepository jobGroupRepository;

    @Override
    public JobUser copySourceToTarget(JobUserEntity jobUserEntity, JobUser jobUser) {
        jobUser.setId(jobUserEntity.getId());
        jobUser.setUsername(jobUserEntity.getUsername());
        jobUser.setPassword("");
        jobUser.setRole(jobUserEntity.getRole());
        jobUser.setPermission(StringUtil.toString(jobUserEntity.getJobGroups().stream().map(jobGroupEntity -> jobGroupEntity.getId().toString()).collect(Collectors.toList())));
        return jobUser;
    }

    @Override
    public JobUserEntity copyTargetToSource(JobUser jobUser, JobUserEntity jobUserEntity) {
        jobUserEntity.setUsername(jobUser.getUsername());
        if (StringUtils.hasText(jobUser.getPassword())) {
            jobUserEntity.setPassword(DigestUtils.md5DigestAsHex(jobUser.getPassword().getBytes(StandardCharsets.UTF_8)));
        }
        jobUserEntity.setRole(jobUser.getRole());
        jobUserEntity.setJobGroups(StringUtil.toList(jobUser.getPermission()).stream().map(jobGroupId -> jobGroupRepository.getOne(Long.parseLong(jobGroupId))).collect(Collectors.toList()));
        jobUserEntity.setId(jobUser.getId());
        return jobUserEntity;
    }
}
