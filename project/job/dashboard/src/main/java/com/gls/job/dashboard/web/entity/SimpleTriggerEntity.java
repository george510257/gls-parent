package com.gls.job.dashboard.web.entity;

import com.gls.job.dashboard.web.entity.enums.MisfireInstruction;
import com.gls.starter.data.jpa.annotations.Comment;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Entity;

/**
 * @author george
 */
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Comment("简单触发器信息表")
public class SimpleTriggerEntity extends TriggerEntity {

    @Comment("间隔时间")
    private Long interval = 0L;

    @Comment("重复次数")
    private Integer repeatCount = 0;

    @Comment("失败说明")
    private MisfireInstruction misfireInstruction = MisfireInstruction.SIMPLE_MISFIRE_INSTRUCTION_SMART_POLICY;
}
