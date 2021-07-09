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
@Comment("模型推理结果表")
public class ModelResultEntity extends BaseEntity {
    @Column
    @Comment("语音通话表ID")
    private Long extractCheckAudioId;
    @Column(length = 100)
    @Comment("标签项")
    private String label;
    @Column(length = 50)
    @Comment("语义类别,语义标签才有值")
    private String category;
    @Column(length = 50)
    @Comment("语音标签违规项目在语音中的起始位置使用'-'分割，例23-59")
    private String paragraph;
    @Column(length = 32)
    @Comment(" 标签来源：token：整通推理，single：单句推理")
    private String sourceType;
    @Column
    @Comment("类型  1.语义标签 2.语音标签 3.整通标签 4.复合标签")
    private Integer type;
}
