package com.gls.job.admin.web.repository.custom;

import com.gls.job.admin.web.entity.JobGroupEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author george
 */
public interface JobGroupCustomRepository {

    /**
     * 根据appname和title模糊查询
     *
     * @param appname
     * @param title
     * @param pageable
     * @return
     */
    Page<JobGroupEntity> loadByCustomParam(String appname, String title, Pageable pageable);
}
