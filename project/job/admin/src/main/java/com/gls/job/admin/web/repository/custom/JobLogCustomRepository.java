package com.gls.job.admin.web.repository.custom;

import com.gls.job.admin.web.entity.JobLogEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author george
 */
public interface JobLogCustomRepository {

    /**
     * 根据定制条件获取
     *
     * @param jobGroupId
     * @param jobInfoId
     * @param triggerTimeStart
     * @param triggerTimeEnd
     * @param logStatus
     * @param pageable
     * @return
     */
    Page<JobLogEntity> loadByCustomParam(Long jobGroupId, Long jobInfoId, Timestamp triggerTimeStart, Timestamp triggerTimeEnd, Integer logStatus, Pageable pageable);

    /**
     * 根据定制条件获取ID
     *
     * @param jobGroupId
     * @param jobInfoId
     * @param clearBeforeTime
     * @param clearBeforeNum
     * @param pageSize
     * @return
     */
    List<JobLogEntity> findClearLogIds(Long jobGroupId, Long jobInfoId, Timestamp clearBeforeTime, Integer clearBeforeNum, Integer pageSize);
}
