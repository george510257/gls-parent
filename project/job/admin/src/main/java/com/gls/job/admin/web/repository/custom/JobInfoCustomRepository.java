package com.gls.job.admin.web.repository.custom;

import com.gls.job.admin.web.entity.JobInfoEntity;
import org.springframework.data.domain.Page;

/**
 * @author george
 */
public interface JobInfoCustomRepository {
    /**
     * get Page
     *
     * @param jobGroupId
     * @param triggerStatus
     * @param jobDesc
     * @param executorHandler
     * @param author
     * @param page
     * @param size
     * @return
     */
    Page<JobInfoEntity> getPage(Long jobGroupId, Boolean triggerStatus, String jobDesc, String executorHandler, String author, int page, int size);
}
