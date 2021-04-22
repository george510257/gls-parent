package com.gls.job.admin.web.entity;

import com.gls.job.admin.web.entity.enums.ExecutorRouteStrategy;
import com.gls.job.admin.web.entity.enums.MisfireStrategy;
import com.gls.job.admin.web.entity.enums.ScheduleType;
import com.gls.job.core.api.model.enums.ExecutorBlockStrategy;
import com.gls.job.core.api.model.enums.GlueType;
import lombok.Data;

import java.util.Date;

/**
 * gls-job info
 *
 * @author george  2016-1-12 18:25:49
 */
@Data
public class XxlJobInfo {

    private int id;                // 主键ID

    private int jobGroup;        // 执行器主键ID
    private String jobDesc;

    private Date addTime;
    private Date updateTime;

    private String author;        // 负责人
    private String alarmEmail;    // 报警邮件

    private ScheduleType scheduleType;            // 调度类型
    private String scheduleConf;            // 调度配置，值含义取决于调度类型
    private MisfireStrategy misfireStrategy;            // 调度过期策略

    private ExecutorRouteStrategy executorRouteStrategy;    // 执行器路由策略
    private String executorHandler;            // 执行器，任务Handler名称
    private String executorParam;            // 执行器，任务参数
    private ExecutorBlockStrategy executorBlockStrategy;    // 阻塞处理策略
    private int executorTimeout;            // 任务执行超时时间，单位秒
    private int executorFailRetryCount;        // 失败重试次数

    private GlueType glueType;        // GLUE类型	#com.gls.job.core.glue.GlueTypeEnum
    private String glueSource;        // GLUE源代码
    private String glueRemark;        // GLUE备注
    private Date glueUpdateTime;    // GLUE更新时间

    private String childJobId;        // 子任务ID，多个逗号分隔

    private int triggerStatus;        // 调度状态：0-停止，1-运行
    private long triggerLastTime;    // 上次调度时间
    private long triggerNextTime;    // 下次调度时间

}