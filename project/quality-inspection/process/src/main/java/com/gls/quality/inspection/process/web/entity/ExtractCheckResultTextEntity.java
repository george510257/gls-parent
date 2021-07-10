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
@Comment("质检结果-会话文本")
public class ExtractCheckResultTextEntity extends BaseEntity {
    @Comment("质检结果id")
    @ManyToOne
    private ExtractCheckResultEntity extractCheckResult;
    @Comment("质检会话id")
    @ManyToOne
    private ExtractCheckAudioEntity extractCheckAudio;
    @Comment("会话文本id")
    @ManyToOne
    private ExtractCheckAudioTextEntity extractCheckAudioText;
    @Comment("违规关键字")
    private String info;
    @Comment("分数")
    private Double violationsScore;
    @Comment("是否误检 1 是，0 否")
    private Integer isError;
}
