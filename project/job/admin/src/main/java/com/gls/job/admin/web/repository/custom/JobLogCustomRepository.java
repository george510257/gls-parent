package com.gls.job.admin.web.repository.custom;

import com.gls.job.admin.web.entity.JobLogEntity;
import org.springframework.data.domain.Page;

import java.util.Date;

/**
 * @author george
 */
public interface JobLogCustomRepository {

    /**
     * get Page
     *
     * @param jobGroupId
     * @param jobInfoId
     * @param triggerTimeFrom
     * @param triggerTimeTo
     * @param logStatus
     * @param page
     * @param size
     * @return
     */
    Page<JobLogEntity> getPage(Long jobGroupId, Long jobInfoId, Date triggerTimeFrom, Date triggerTimeTo, Integer logStatus, int page, int size);

}
