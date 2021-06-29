package com.gls.job.admin.web.model;

import com.gls.framework.api.model.BaseModel;
import com.gls.job.admin.constants.ExecutorRouteStrategy;
import com.gls.job.admin.constants.MisfireStrategy;
import com.gls.job.admin.constants.ScheduleType;
import com.gls.job.core.constants.ExecutorBlockStrategy;
import com.gls.job.core.constants.GlueType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * @author george
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class JobInfo extends BaseModel {
    /**
     * 执行器主键ID
     */
    @NotBlank
    private Long jobGroup;
    @NotBlank
    private String jobDesc;
    private Date addTime;
    private Date updateTime;
    /**
     * 负责人
     */
    @NotBlank
    private String author;
    /**
     * 报警邮件
     */
    private String alarmEmail;
    /**
     * 调度类型
     */
    @NotBlank
    private ScheduleType scheduleType;
    /**
     * 调度配置，值含义取决于调度类型
     */
    private String scheduleConf;
    /**
     * 调度过期策略
     */
    @NotBlank
    private MisfireStrategy misfireStrategy;
    /**
     * 执行器路由策略
     */
    @NotBlank
    private ExecutorRouteStrategy executorRouteStrategy;
    /**
     * 执行器，任务Handler名称
     */
    private String executorHandler;
    /**
     * 执行器，任务参数
     */
    private String executorParam;
    /**
     * 阻塞处理策略
     */
    @NotBlank
    private ExecutorBlockStrategy executorBlockStrategy;
    /**
     * 任务执行超时时间，单位秒
     */
    private int executorTimeout;
    /**
     * 失败重试次数
     */
    private int executorFailRetryCount;
    /**
     * GLUE类型	#com.gls.job.core.constants.GlueType
     */
    @NotBlank
    private GlueType glueType;
    /**
     * GLUE源代码
     */
    private String glueSource;
    /**
     * GLUE备注
     */
    private String glueRemark;
    /**
     * GLUE更新时间
     */
    private Date glueUpdateTime;
    /**
     * 子任务ID，多个逗号分隔
     */
    private String childJobId;
    /**
     * 调度状态：0-停止，1-运行
     */
    private int triggerStatus;
    /**
     * 上次调度时间
     */
    private long triggerLastTime;
    /**
     * 下次调度时间
     */
    private long triggerNextTime;
}
