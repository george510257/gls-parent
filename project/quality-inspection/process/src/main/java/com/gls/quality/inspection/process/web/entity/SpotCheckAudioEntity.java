package com.gls.quality.inspection.process.web.entity;

import com.gls.starter.data.jpa.annotations.Comment;
import com.gls.starter.data.jpa.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.Entity;

/**
 * @author george
 */
@Entity
@Data
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Comment("抽检会话表")
public class SpotCheckAudioEntity extends BaseEntity {
    @Comment("抽检计划id")
    private Long spotCheckId;
    @Comment("会话id")
    private Long extractCheckAudioId;
    @Comment("质检员id")
    private Long userId;
    @Comment("抽检会话状态 0:待处理，1:已处理，2:已失效 ")
    private Integer status;
}
