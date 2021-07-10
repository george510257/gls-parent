package com.gls.quality.inspection.process.web.entity;

import com.gls.starter.data.jpa.annotations.Comment;
import com.gls.starter.data.jpa.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 * @author george
 */
@Entity
@Data
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Comment("模型")
public class ModelEntity extends BaseEntity {
    @Comment("行业分类id")
    @ManyToOne
    private IndustryCategoryEntity industryCategory;
    @Comment("模型状态 1.开启 2.关闭")
    private Integer status;
    @Comment("是否已发布，0：未发布：1已发布")
    private Integer released;
    @Comment("语音超时未回复时间, -1:关闭")
    private Integer voiceTimeout;
    @Comment("客户未同意, 等待关闭秒数")
    private Integer voiceShutdown;
    @Comment("语音语速过快, 每秒输出字数")
    private Integer voiceFast;
    @Comment("语音分贝")
    private Integer voiceDb;
    @Comment("抢话间隔")
    private Integer voiceInterrupt;
    @Comment("抢话间隔开关状态1.开启 2.关闭")
    private Integer voiceInterruptStatus;
    @Comment("模型标签相似度阀值 1-100")
    private Integer labelScore;
    @Comment("推理调用-token模块：1开启，2关闭")
    private Integer tokenModule;
    @Comment("推理调用-语义模块：1开启，2关闭")
    private Integer semanticsModule;
}
