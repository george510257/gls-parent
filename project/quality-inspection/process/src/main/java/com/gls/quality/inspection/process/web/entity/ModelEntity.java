package com.gls.quality.inspection.process.web.entity;

import com.gls.starter.data.jpa.annotations.Comment;
import com.gls.starter.data.jpa.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;

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
    @Column(length = 20)
    @Comment("模型名称")
    private String name;
    @Column
    @Comment("行业分类id")
    private Integer industryCategoryId;
    @Column(length = 20)
    @Comment("行业分类")
    private String industryCategory;
    @Column
    @Comment("模型状态 1.开启 2.关闭")
    private Integer status;
    @Column
    @Comment("是否已发布，0：未发布：1已发布")
    private Integer released;
    @Column
    @Comment("创建人id")
    private Integer createBy;
    @Column
    @Comment("语音超时未回复时间, -1:关闭")
    private Integer voiceTimeout;
    @Column
    @Comment("客户未同意, 等待关闭秒数")
    private Integer voiceShutdown;
    @Column
    @Comment("语音语速过快, 每秒输出字数")
    private Integer voiceFast;
    @Column
    @Comment("语音分贝")
    private Integer voiceDb;
    @Column
    @Comment("抢话间隔")
    private Integer voiceInterrupt;
    @Column
    @Comment("抢话间隔开关状态1.开启 2.关闭")
    private Integer voiceInterruptStatus;
    @Column
    @Comment("模型标签相似度阀值 1-100")
    private Integer labelScore;
    @Column
    @Comment("推理调用-token模块：1开启，2关闭")
    private Integer tokenModule;
    @Column
    @Comment("推理调用-语义模块：1开启，2关闭")
    private Integer semanticsModule;
}
