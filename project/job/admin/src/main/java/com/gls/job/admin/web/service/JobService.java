package com.gls.job.admin.web.service;

import com.gls.job.admin.web.model.JobInfo;
import com.gls.job.core.api.model.Result;

import java.util.Date;
import java.util.Map;

/**
 * core job action for gls-job
 *
 * @author xuxueli 2016-5-28 15:30:33
 */
public interface JobService {

    /**
     * page list
     *
     * @param start
     * @param length
     * @param jobGroup
     * @param jobDesc
     * @param executorHandler
     * @param author
     * @return
     */
    Map<String, Object> pageList(int start, int length, Long jobGroup, int triggerStatus, String jobDesc, String executorHandler, String author);

    /**
     * add job
     *
     * @param jobInfo
     * @return
     */
    Result<String> add(JobInfo jobInfo);

    /**
     * update job
     *
     * @param jobInfo
     * @return
     */
    Result<String> update(JobInfo jobInfo);

    /**
     * remove job
     * *
     *
     * @param id
     * @return
     */
    Result<String> remove(Long id);

    /**
     * start job
     *
     * @param id
     * @return
     */
    Result<String> start(Long id);

    /**
     * stop job
     *
     * @param id
     * @return
     */
    Result<String> stop(Long id);

    /**
     * dashboard info
     *
     * @return
     */
    Map<String, Object> dashboardInfo();

    /**
     * chart info
     *
     * @param startDate
     * @param endDate
     * @return
     */
    Result<Map<String, Object>> chartInfo(Date startDate, Date endDate);

}
