package com.gls.job.admin.web.repository;

import com.gls.job.admin.web.entity.JobLogReportEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * job log
 *
 * @author xuxueli 2019-11-22
 */
@Mapper
public interface JobLogReportRepository {

    public int save(JobLogReportEntity jobLogReportEntity);

    public int update(JobLogReportEntity jobLogReportEntity);

    public List<JobLogReportEntity> queryLogReport(@Param("triggerDayFrom") Date triggerDayFrom,
                                                   @Param("triggerDayTo") Date triggerDayTo);

    public JobLogReportEntity queryLogReportTotal();

}
