package com.gls.quality.inspection.process.web.entity;

import com.gls.starter.data.jpa.annotations.Comment;
import com.gls.starter.data.jpa.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.Column;
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
@Comment("模型推理结果会话表")
public class ModelResultTextEntity extends BaseEntity {
    @Comment("模型推理结果id")
    @ManyToOne
    private ModelResultEntity modelResult;
    @Comment("质检会话id")
    @ManyToOne
    private ExtractCheckAudioEntity extractCheckAudio;
    @Comment("会话文本id")
    @ManyToOne
    private ExtractCheckAudioTextEntity extractCheckAudioText;
    @Column(length = 65535)
    @Comment("违规关键字")
    private String info;
}
