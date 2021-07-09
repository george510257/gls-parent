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
@Comment("质检结果-会话文本")
public class ExtractCheckResultTextEntity extends BaseEntity {
    @Column
    @Comment("质检结果id")
    private Integer extractCheckResultId;
    @Column
    @Comment("质检会话id")
    private Integer extractCheckAudioId;
    @Column
    @Comment("会话文本id")
    private Integer extractCheckAudioTextId;
    @Column
    @Comment("违规关键字")
    private String info;
    @Column
    @Comment("分数")
    private Double violationsScore;
    @Column
    @Comment("是否误检 1 是，0 否")
    private Integer isError;
}
