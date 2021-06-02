package com.gls.job.admin.web.entity;

import com.gls.job.admin.core.enums.ExecutorRouteStrategy;
import com.gls.job.admin.core.enums.MisfireStrategy;
import com.gls.job.admin.core.enums.ScheduleType;
import com.gls.job.core.enums.ExecutorBlockStrategy;
import com.gls.job.core.enums.GlueType;
import com.gls.starter.data.jpa.annotations.Comment;
import com.gls.starter.data.jpa.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

/**
 * gls-job info
 *
 * @author xuxueli  2016-1-12 18:25:49
 */
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "job_info")
@Comment("执行器信息表")
public class JobInfoEntity extends BaseEntity {

    @ManyToOne
    @Comment("执行器主键ID")
    private JobGroupEntity jobGroup;

    private String jobDesc;

    @Comment("负责人")
    private String author;
    @Comment("报警邮件")
    private String alarmEmail;

    @Comment("调度类型")
    @Enumerated(EnumType.STRING)
    private ScheduleType scheduleType;
    @Comment("调度配置，值含义取决于调度类型")
    private String scheduleConf;
    @Comment("调度过期策略")
    @Enumerated(EnumType.STRING)
    private MisfireStrategy misfireStrategy;

    @Comment("执行器:路由策略")
    @Enumerated(EnumType.STRING)
    private ExecutorRouteStrategy executorRouteStrategy;
    @Comment("执行器:任务Handler名称")
    private String executorHandler;
    @Comment("执行器:任务参数")
    private String executorParam;
    @Comment("执行器:阻塞处理策略")
    @Enumerated(EnumType.STRING)
    private ExecutorBlockStrategy executorBlockStrategy;
    @Comment("执行器:任务执行超时时间，单位秒")
    private Integer executorTimeout;
    @Comment("执行器:失败重试次数")
    private Integer executorFailRetryCount;

    @Comment("GLUE类型")
    @Enumerated(EnumType.STRING)
    private GlueType glueType;
    @Comment("GLUE源代码")
    private String glueSource;
    @Comment("GLUE备注")
    private String glueRemark;
    @Comment("GLUE更新时间")
    private Timestamp glueUpdateTime;

    @Comment("子任务")
    @ManyToMany
    private List<JobInfoEntity> childJobs;

    @Comment("调度状态：false-停止,true-运行")
    private Boolean triggerStatus;
    @Comment("上次调度时间")
    private Timestamp triggerLastTime;
    @Comment("下次调度时间")
    private Timestamp triggerNextTime;

}
