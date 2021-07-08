package com.gls.job.dashboard.web.entity;

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
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Accessors(chain = true)
@Comment("执行日志信息表")
public class JobLogEntity extends BaseEntity {
    @Comment("执行器主键ID")
    @ManyToOne
    private JobGroupEntity jobGroup;
    @Comment("任务，主键ID")
    @ManyToOne
    private JobInfoEntity jobInfo;
    @Comment("执行器地址，本次执行的地址")
    private String executorAddress;
    @Comment("执行器任务handler")
    private String executorHandler;
    @Comment("执行器任务参数")
    private String executorParam;
    @Comment("执行器任务分片参数，格式如 1/2")
    private String executorShardingParam;
    @Comment("失败重试次数")
    private Integer executorFailRetryCount;
    @Comment("调度-时间")
    private Date triggerTime;
    @Comment("调度-结果")
    private Integer triggerCode;
    @Comment("调度-日志")
    private String triggerMsg;
    @Comment("执行-时间")
    private Date handleTime;
    @Comment("执行-状态")
    private Integer handleCode;
    @Comment("执行-日志")
    private String handleMsg;
    @Comment("告警状态：0-默认、1-无需告警、2-告警成功、3-告警失败")
    private Integer alarmStatus;
}
