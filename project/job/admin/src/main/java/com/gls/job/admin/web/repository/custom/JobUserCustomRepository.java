package com.gls.job.admin.web.repository.custom;

import com.gls.job.admin.web.entity.JobUserEntity;
import org.springframework.data.domain.Page;

/**
 * @author george
 */
public interface JobUserCustomRepository {

    /**
     * get Page
     *
     * @param username
     * @param role
     * @param page
     * @param size
     * @return
     */
    Page<JobUserEntity> getPage(String username, Integer role, int page, int size);
}
