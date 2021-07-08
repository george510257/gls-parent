package com.gls.job.dashboard.web.entity;

import com.gls.job.core.constants.*;
import com.gls.starter.data.jpa.annotations.Comment;
import com.gls.starter.data.jpa.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import java.util.List;

/**
 * @author george
 */
@Entity
@Data
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Comment("任务信息表")
public class JobInfoEntity extends BaseEntity {
    @Comment("执行器主键ID")
    @ManyToOne
    private JobGroupEntity jobGroup;
    private String jobDesc;
    @Comment("作者")
    private String author;
    @Comment("报警邮件")
    private String alarmEmail;
    @Comment("调度类型")
    private ScheduleType scheduleType = ScheduleType.NONE;
    @Comment("调度配置，值含义取决于调度类型")
    private String scheduleConf;
    @Comment("调度过期策略")
    private MisfireStrategy misfireStrategy = MisfireStrategy.DO_NOTHING;
    @Comment("执行器路由策略")
    private ExecutorRouteStrategy executorRouteStrategy;
    @Comment("执行器任务handler")
    private String executorHandler;
    @Comment("执行器任务参数")
    private String executorParam;
    @Comment("阻塞处理策略")
    private ExecutorBlockStrategy executorBlockStrategy;
    @Comment("任务执行超时时间，单位秒")
    private Integer executorTimeout = 0;
    @Comment("失败重试次数")
    private Integer executorFailRetryCount = 0;
    @Comment("GLUE类型")
    private GlueType glueType;
    @ManyToOne
    private JobLogGlueEntity jobLogGlue;
    @Comment("子任务ID，多个逗号分隔")
    @ManyToMany
    private List<JobInfoEntity> childJobs;
    @Comment("调度状态：0-停止，1-运行")
    private Integer triggerStatus;
    @Comment("上次调度时间")
    private Long triggerLastTime;
    @Comment("下次调度时间")
    private Long triggerNextTime;
}
