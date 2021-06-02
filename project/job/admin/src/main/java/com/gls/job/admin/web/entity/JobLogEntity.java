package com.gls.job.admin.web.entity;

import com.gls.starter.data.jpa.annotations.Comment;
import com.gls.starter.data.jpa.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.sql.Timestamp;

/**
 * gls-job log, used to track trigger process
 *
 * @author xuxueli  2015-12-19 23:19:09
 */
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "job_log")
@Comment("执行日志信息表")
public class JobLogEntity extends BaseEntity {

    // job info

    @ManyToOne
    private JobInfoEntity jobInfo;

    // execute info

    private String executorAddress;
    private String executorHandler;
    private String executorParam;
    private String executorShardingParam;
    private Integer executorFailRetryCount;

    // trigger info

    private Timestamp triggerTime;
    private Integer triggerCode;
    private String triggerMsg;

    // handle info

    private Timestamp handleTime;
    private Integer handleCode;
    private String handleMsg;

    // alarm info

    private Integer alarmStatus;

}
