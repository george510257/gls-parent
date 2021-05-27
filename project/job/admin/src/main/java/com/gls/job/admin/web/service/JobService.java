package com.gls.job.admin.web.service;

import com.gls.job.admin.web.entity.JobInfoEntity;
import com.gls.job.core.biz.model.ReturnT;

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
    public Map<String, Object> pageList(int start, int length, int jobGroup, int triggerStatus, String jobDesc, String executorHandler, String author);

    /**
     * add job
     *
     * @param jobInfoEntity
     * @return
     */
    public ReturnT<String> add(JobInfoEntity jobInfoEntity);

    /**
     * update job
     *
     * @param jobInfoEntity
     * @return
     */
    public ReturnT<String> update(JobInfoEntity jobInfoEntity);

    /**
     * remove job
     * *
     *
     * @param id
     * @return
     */
    public ReturnT<String> remove(int id);

    /**
     * start job
     *
     * @param id
     * @return
     */
    public ReturnT<String> start(int id);

    /**
     * stop job
     *
     * @param id
     * @return
     */
    public ReturnT<String> stop(int id);

    /**
     * dashboard info
     *
     * @return
     */
    public Map<String, Object> dashboardInfo();

    /**
     * chart info
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public ReturnT<Map<String, Object>> chartInfo(Date startDate, Date endDate);

}
