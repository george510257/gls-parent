package com.gls.job.admin.web.repository.custom;

import com.gls.job.admin.web.entity.JobInfoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author george
 */
public interface JobInfoCustomRepository {

    /**
     * 根据定制条件获取
     *
     * @param jobGroupId
     * @param triggerStatus
     * @param jobDesc
     * @param executorHandler
     * @param author
     * @param pageable
     * @return
     */
    Page<JobInfoEntity> loadByCustomParam(Long jobGroupId, Boolean triggerStatus, String jobDesc, String executorHandler, String author, Pageable pageable);
}
