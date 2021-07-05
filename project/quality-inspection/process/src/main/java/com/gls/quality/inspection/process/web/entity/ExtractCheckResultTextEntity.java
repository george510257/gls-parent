package com.gls.quality.inspection.process.web.entity;

import com.gls.starter.data.jpa.annotations.Comment;
import com.gls.starter.data.jpa.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Entity;

/**
 * @author george
 */
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Comment("客户端信息表")
public class ExtractCheckResultTextEntity extends BaseEntity {
    private Integer extractCheckResultId;
    private Integer extractCheckAudioId;
    private Integer extractCheckAudioTextId;
    private String info;
    private Double violationsScore;
    private Boolean isError;
}
