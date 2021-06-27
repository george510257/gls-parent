package com.gls.job.admin.web.repository.custom;

import com.gls.job.admin.web.entity.JobGroupEntity;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author george
 */
public interface JobGroupCustomRepository {
    /**
     * get All
     *
     * @return
     */
    List<JobGroupEntity> getAllList();

    /**
     * get By Appname And Title
     *
     * @param appname
     * @param title
     * @param page
     * @param size
     * @return
     */
    Page<JobGroupEntity> getPage(String appname, String title, int page, int size);
}
