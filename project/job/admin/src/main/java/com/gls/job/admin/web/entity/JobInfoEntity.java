package com.gls.job.admin.web.entity;

import com.gls.job.admin.constants.ExecutorRouteStrategy;
import com.gls.job.admin.constants.MisfireStrategy;
import com.gls.job.admin.constants.ScheduleType;
import com.gls.job.core.constants.ExecutorBlockStrategy;
import com.gls.job.core.constants.GlueType;
import com.gls.starter.data.jpa.annotations.Comment;
import com.gls.starter.data.jpa.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * @author george
 */
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Comment("任务信息表")
public class JobInfoEntity extends BaseEntity {
    @Comment("执行器主键ID")
    @ManyToOne
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
    @Comment("执行器路由策略")
    @Enumerated(EnumType.STRING)
    private ExecutorRouteStrategy executorRouteStrategy;
    @Comment("执行器，任务Handler名称")
    private String executorHandler;
    @Comment("执行器，任务参数")
    private String executorParam;
    @Comment("阻塞处理策略")
    @Enumerated(EnumType.STRING)
    private ExecutorBlockStrategy executorBlockStrategy;
    @Comment("任务执行超时时间，单位秒")
    private Integer executorTimeout;
    @Comment("失败重试次数")
    private Integer executorFailRetryCount;
    @Comment("GLUE类型")
    @Enumerated(EnumType.STRING)
    private GlueType glueType;
    @Comment("GLUE源代码")
    private String glueSource;
    @Comment("GLUE备注")
    private String glueRemark;
    @Comment("GLUE更新时间")
    private Date glueUpdateTime;
    @Comment("子任务ID")
    @ManyToMany
    private List<JobInfoEntity> childJobs;
    @Comment("调度状态：false-停止，true-运行")
    private Boolean triggerStatus;
    @Comment("上次调度时间")
    private Date triggerLastTime;
    @Comment("下次调度时间")
    private Date triggerNextTime;
}
