package com.gls.job.admin.web.dao;

import com.gls.job.admin.web.entity.XxlJobLogReport;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * job log
 *
 * @author george 2019-11-22
 */
@Mapper
public interface XxlJobLogReportDao {

    int save(XxlJobLogReport glsJobLogReport);

    int update(XxlJobLogReport glsJobLogReport);

    List<XxlJobLogReport> queryLogReport(@Param("triggerDayFrom") Date triggerDayFrom,
                                         @Param("triggerDayTo") Date triggerDayTo);

    XxlJobLogReport queryLogReportTotal();

}
