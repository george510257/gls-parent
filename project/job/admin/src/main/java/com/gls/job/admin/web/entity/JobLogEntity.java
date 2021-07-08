package com.gls.job.admin.web.entity;

import com.gls.starter.data.jpa.annotations.Comment;
import com.gls.starter.data.jpa.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.util.Date;

/**
 * @author george
 */
@Entity
@Data
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Comment("任务日志信息表")
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
    private Date triggerTime;
    private Integer triggerCode;
    private String triggerMsg;
    // handle info
    private Date handleTime;
    private Integer handleCode;
    private String handleMsg;
    // alarm info
    private Integer alarmStatus;
}
