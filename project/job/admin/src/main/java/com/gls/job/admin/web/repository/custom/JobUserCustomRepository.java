package com.gls.job.admin.web.repository.custom;

import com.gls.job.admin.web.entity.JobUserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author george
 */
public interface JobUserCustomRepository {

    /**
     * 根据定制条件获取
     *
     * @param username
     * @param role
     * @param pageable
     * @return
     */
    Page<JobUserEntity> loadByCustomParam(String username, Integer role, Pageable pageable);
}
