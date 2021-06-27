package com.gls.job.admin.web.converter;

import com.gls.framework.core.base.BaseConverter;
import com.gls.job.admin.web.entity.JobLogReportEntity;
import com.gls.job.admin.web.model.JobLogReport;
import org.springframework.stereotype.Component;

/**
 * @author george
 */
@Component
public class JobLogReportConverter implements BaseConverter<JobLogReportEntity, JobLogReport> {
    @Override
    public JobLogReport copySourceToTarget(JobLogReportEntity jobLogReportEntity, JobLogReport jobLogReport) {
        jobLogReport.setId(jobLogReportEntity.getId());
        jobLogReport.setTriggerDay(jobLogReportEntity.getTriggerDay());
        jobLogReport.setRunningCount(jobLogReportEntity.getRunningCount());
        jobLogReport.setSucCount(jobLogReportEntity.getSucCount());
        jobLogReport.setFailCount(jobLogReportEntity.getFailCount());
        return jobLogReport;
    }

    @Override
    public JobLogReportEntity copyTargetToSource(JobLogReport jobLogReport, JobLogReportEntity jobLogReportEntity) {
        jobLogReportEntity.setTriggerDay(jobLogReport.getTriggerDay());
        jobLogReportEntity.setRunningCount(jobLogReport.getRunningCount());
        jobLogReportEntity.setSucCount(jobLogReport.getSucCount());
        jobLogReportEntity.setFailCount(jobLogReport.getFailCount());
        jobLogReportEntity.setId(jobLogReport.getId());
        return jobLogReportEntity;
    }
}
