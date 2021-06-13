package com.gls.job.admin.web.service;

import com.gls.job.admin.web.model.XxlJobInfo;
import com.gls.job.core.api.model.Result;

import java.util.Date;
import java.util.Map;

/**
 * core job action for xxl-job
 *
 * @author xuxueli 2016-5-28 15:30:33
 */
public interface XxlJobService {

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
    public Map<String, Object> pageList(int start, int length, Long jobGroup, int triggerStatus, String jobDesc, String executorHandler, String author);

    /**
     * add job
     *
     * @param jobInfo
     * @return
     */
    public Result<String> add(XxlJobInfo jobInfo);

    /**
     * update job
     *
     * @param jobInfo
     * @return
     */
    public Result<String> update(XxlJobInfo jobInfo);

    /**
     * remove job
     * *
     *
     * @param id
     * @return
     */
    public Result<String> remove(Long id);

    /**
     * start job
     *
     * @param id
     * @return
     */
    public Result<String> start(Long id);

    /**
     * stop job
     *
     * @param id
     * @return
     */
    public Result<String> stop(Long id);

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
    public Result<Map<String, Object>> chartInfo(Date startDate, Date endDate);

}
