package com.gls.job.admin.web.repository;

import com.gls.job.admin.web.entity.JobLogReportEntity;
import com.gls.starter.data.jpa.base.BaseEntityRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

/**
 * job log
 *
 * @author xuxueli 2019-11-22
 */
public interface JobLogReportRepository extends BaseEntityRepository<JobLogReportEntity> {

    /**
     * 根据triggerDay获取
     *
     * @param triggerDayFrom
     * @param triggerDayTo
     * @return
     */
    List<JobLogReportEntity> findByTriggerDayBetweenOrderByTriggerDayAsc(Timestamp triggerDayFrom, Timestamp triggerDayTo);

    /**
     * 获取合计
     *
     * @return
     */
    @Query(value = "select sum(jobLogReport.runningCount) as runningCountSum, " +
            "sum(jobLogReport.sucCount) as sucCountSum, " +
            "sum(jobLogReport.failCount) as failCountSum " +
            "from JobLogReportEntity jobLogReport")
    Map<String, Object> findLogReportTotal();
}
