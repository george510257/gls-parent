package com.gls.job.admin.web.repository.custom;

import com.gls.job.admin.web.entity.JobUserEntity;
import org.springframework.data.domain.Page;

/**
 * @author george
 */
public interface JobUserCustomRepository {

    Page<JobUserEntity> getPage(String username, Integer role, int page, int size);
}
