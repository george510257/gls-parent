package com.gls.job.admin.web.service;

import com.gls.job.admin.web.model.JobInfo;
import com.gls.job.admin.web.model.query.QueryJobInfo;
import com.gls.starter.data.jpa.base.BaseService;

import java.util.Date;
import java.util.List;

/**
 * @author george
 */
public interface JobInfoService extends BaseService<JobInfo, QueryJobInfo> {
    /**
     * startJobInfo
     *
     * @param jobInfoId
     */
    void start(Long jobInfoId);

    /**
     * stop
     *
     * @param jobInfoId
     */
    void stop(Long jobInfoId);

    /**
     * getByJobGroupId
     *
     * @param jobGroupId
     * @return
     */
    List<JobInfo> getByJobGroupId(Long jobGroupId);

    /**
     * getScheduleJob
     *
     * @param date
     * @param preReadCount
     * @return
     */
    List<JobInfo> getScheduleJob(Date date, int preReadCount);
}
