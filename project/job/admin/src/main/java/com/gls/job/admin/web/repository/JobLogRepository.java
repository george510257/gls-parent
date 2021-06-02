package com.gls.job.admin.web.repository;

import com.gls.job.admin.web.entity.JobLogEntity;
import com.gls.job.admin.web.repository.custom.JobLogCustomRepository;
import com.gls.starter.data.jpa.base.BaseEntityRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * job log
 *
 * @author xuxueli 2016-1-12 18:03:06
 */
public interface JobLogRepository extends BaseEntityRepository<JobLogEntity>, JobLogCustomRepository {

    /**
     * 根据triggerCode获取合计
     *
     * @param triggerCodeFrom
     * @param triggerCodeTo
     * @return
     */
    @Query(value = "select count(jobLog.handleCode) as triggerDayCount, " +
            "sum(case when (jobLog.triggerCode in (0,200) and jobLog.handleCode = 0) then 1 else 0 end ) as triggerDayCountRunning, " +
            "sum(case when jobLog.handleCode = 200 then 1 else 0 end ) as triggerDayCountSuc " +
            "from JobLogEntity jobLog where jobLog.triggerCode between :triggerCodeFrom and :triggerCodeTo")
    Map<String, Object> findLogReport(@Param("triggerCodeFrom") Date triggerCodeFrom, @Param("triggerCodeTo") Date triggerCodeTo);

    /**
     * 获取失败的日志
     *
     * @param pageable
     * @return
     */
    @Query(value = "select jobLog from JobLogEntity jobLog " +
            "where jobLog.alarmStatus = 0 " +
            "and not ((jobLog.triggerCode in (0,200) and jobLog.handleCode = 0) or jobLog.handleCode = 200)")
    List<JobLogEntity> findFailJobLog(Pageable pageable);

    /**
     * 根据triggerTime获取
     *
     * @param triggerTime
     * @return
     */
    @Query(value = "select jobLog from JobLogEntity jobLog " +
            "left join JobRegistryEntity jobRegistry on jobLog.executorAddress = jobRegistry.registryValue " +
            "where jobLog.triggerCode = 200 " +
            "and jobLog.handleCode = 0 " +
            "and jobLog.triggerTime <= :triggerTime " +
            "and jobRegistry.id is null ")
    List<JobLogEntity> findLostJobLog(@Param("triggerTime") Timestamp triggerTime);

}
