package com.gls.job.admin.web.repository;

import com.gls.job.admin.web.entity.JobLogReportEntity;
import com.gls.starter.data.jpa.base.BaseEntityRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author george
 */
public interface JobLogReportRepository extends BaseEntityRepository<JobLogReportEntity> {
    /**
     * get By TriggerDay Between
     *
     * @param triggerDayFrom
     * @param triggerDayTo
     * @return
     */
    List<JobLogReportEntity> getByTriggerDayBetween(Date triggerDayFrom, Date triggerDayTo);

    /**
     * getByTriggerDay
     *
     * @param triggerDay
     * @return
     */
    Optional<JobLogReportEntity> getByTriggerDay(Date triggerDay);

    /**
     * get Report Total
     *
     * @return
     */
    @Query(value = "select " +
            "SUM(t.running_count) as runningCount, " +
            "SUM(t.suc_count) as sucCount, " +
            "SUM(t.fail_count) as failCount " +
            "from job_log_report_entity t;", nativeQuery = true)
    Map<String, Long> getReportTotal();
}
