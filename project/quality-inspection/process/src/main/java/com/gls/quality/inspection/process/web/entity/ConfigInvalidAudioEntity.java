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
@Comment("系统管理-默认设置-无效会话定义")
public class ConfigInvalidAudioEntity extends BaseEntity {
    @Comment("根据时长定义单选框，0禁用，1启用")
    private Integer defineDurationRadio;
    @Comment("根据时长定义默认秒数")
    private Integer defineDurationSecond;
    @Comment("根据无转写结果定义单选框，0禁用，1启用")
    private Integer defineResultRadio;
    @Comment("根据对话轮次定义单选框，0禁用，1启用")
    private Integer defineRoundRadio;
    @Comment("对话轮次默认次数")
    private Integer defineRoundCount;
}
