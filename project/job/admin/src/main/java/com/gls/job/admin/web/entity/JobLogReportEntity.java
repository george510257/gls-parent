package com.gls.job.admin.web.entity;

import com.gls.starter.data.jpa.annotations.Comment;
import com.gls.starter.data.jpa.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.sql.Timestamp;

/**
 * @author george
 */
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "job_log_report")
@Comment("执行日志Report信息表")
public class JobLogReportEntity extends BaseEntity {

    private Timestamp triggerDay;

    private Integer runningCount;
    private Integer sucCount;
    private Integer failCount;

}
