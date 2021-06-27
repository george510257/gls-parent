package com.gls.job.admin.web.repository;

import com.gls.job.admin.web.entity.JobLogEntity;
import com.gls.job.admin.web.repository.custom.JobLogCustomRepository;
import com.gls.starter.data.jpa.base.BaseEntityRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author george
 */
public interface JobLogRepository extends BaseEntityRepository<JobLogEntity>, JobLogCustomRepository {
    /**
     * delete By JobInfoId
     *
     * @param jobInfoId
     */
    void deleteByJobInfoId(Long jobInfoId);

    /**
     * get Log Report
     *
     * @param triggerTimeFrom
     * @param triggerTimeTo
     * @return
     */
    @Query(value = "select " +
            "count(t.handleCode) as triggerDayCount, " +
            "sum(case when (t.triggerCode in (0,200) and t.handleCode = 0) then 1 else 0 end ) as triggerDayCountRunning," +
            "sum(case when t.handleCode = 200 then 1 else 0 end ) as triggerDayCountSuc " +
            "from JobLogEntity t where t.triggerTime between :triggerTimeFrom and :triggerTimeTo")
    Map<String, Long> getLogReport(@Param("triggerTimeFrom") Date triggerTimeFrom, @Param("triggerTimeTo") Date triggerTimeTo);

    /**
     * get Fail JobLogs
     *
     * @param size
     * @return
     */
    @Query(value = "select t.* from job_log_entity t " +
            "where !((t.trigger_code in (0, 200) and t.handle_code = 0) or t.handle_code = 200) " +
            "and t.alarm_status = 0 " +
            "order by t.id asc " +
            "limit :size", nativeQuery = true)
    List<JobLogEntity> getFailJobLogs(@Param("size") int size);

    /**
     * get Lost JobLogs
     *
     * @param lostTime
     * @return
     */
    @Query(value = "select t from JobLogEntity t left join JobRegistryEntity t1 " +
            "on t.executorAddress = t1.registryValue " +
            "where t.triggerCode = 200 " +
            "and t.handleCode = 0 " +
            "and  t.triggerTime <= :lostTime " +
            "and t1.id is null")
    List<JobLogEntity> getLostJobLogs(@Param("lostTime") Date lostTime);
}
