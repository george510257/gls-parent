package com.gls.job.admin.web.repository;

import com.gls.job.admin.web.entity.JobLogReportEntity;
import com.gls.starter.data.jpa.base.BaseEntityRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;
import java.util.Map;

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
     * get Report Total
     *
     * @return
     */
    @Query(value = "select " +
            "SUM(t.running_count) as running_count, " +
            "SUM(t.suc_count) as suc_count, " +
            "SUM(t.fail_count) as fail_count " +
            "from job_log_report_entity t;", nativeQuery = true)
    List<Map<String, Long>> getReportTotal();
}
