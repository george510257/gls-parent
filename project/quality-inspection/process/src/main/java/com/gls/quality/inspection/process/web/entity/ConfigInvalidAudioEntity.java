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
public class ConfigInvalidAudioEntity extends BaseEntity {
    private Boolean defineDurationRadio;
    private Integer defineDurationSecond;
    private Boolean defineResultRadio;
    private Boolean defineRoundRadio;
    private Integer defineRoundCount;
}
