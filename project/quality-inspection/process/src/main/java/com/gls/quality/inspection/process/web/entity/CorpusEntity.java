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
@Comment("模型会话")
public class CorpusEntity extends BaseEntity {
    @Column
    @Comment("模型id")
    private Long modelId;
    @Column(length = 65535)
    @Comment("语料内容")
    private String content;
    @Column(length = 1000)
    @Comment("音频语料链接")
    private String audioUrl;
    @Column
    @Comment("转写状态 0：未转写，1：转写中，2：转写完成")
    private Integer translateStatus;
    @Column
    @Comment("语料状态 1.文本 2.音频")
    private Integer status;
    @Column
    @Comment("是否标注 1.已标注 2.未标注")
    private Integer isAnnotated;
    @Column
    @Comment("音频时长，毫秒")
    private Integer audioLen;
    @Column
    @Comment("整通标签id")
    private Long totalLabelId;
    @Column
    @Comment("复合标签id")
    private Long complexLabelId;
    @Column(length = 3000)
    @Comment("语义标签 时间戳-语义标签id,...(1596101712-12,...)")
    private String semanticLabelList;
}
