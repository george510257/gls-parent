package com.gls.job.admin.web.dao;

import com.gls.job.admin.web.model.JobLogReport;
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
public interface JobLogReportDao {

    int save(JobLogReport jobLogReport);

    int update(JobLogReport jobLogReport);

    List<JobLogReport> queryLogReport(@Param("triggerDayFrom") Date triggerDayFrom,
                                      @Param("triggerDayTo") Date triggerDayTo);

    JobLogReport queryLogReportTotal();

}
