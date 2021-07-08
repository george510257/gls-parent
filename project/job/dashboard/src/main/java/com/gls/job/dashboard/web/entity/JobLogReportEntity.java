package com.gls.job.dashboard.web.entity;

import com.gls.starter.data.jpa.annotations.Comment;
import com.gls.starter.data.jpa.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import java.util.Date;

/**
 * @author george
 */
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Accessors(chain = true)
@Comment("调度日志报表信息表")
public class JobLogReportEntity extends BaseEntity {
    @Comment("调度-时间")
    private Date triggerDay;
    @Comment("运行中-日志数量")
    private Integer runningCount;
    @Comment("执行成功-日志数量")
    private Integer sucCount;
    @Comment("执行失败-日志数量")
    private Integer failCount;
}
