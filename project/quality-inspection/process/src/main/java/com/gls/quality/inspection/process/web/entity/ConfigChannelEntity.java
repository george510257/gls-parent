package com.gls.quality.inspection.process.web.entity;

import com.gls.starter.data.jpa.annotations.Comment;
import com.gls.starter.data.jpa.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.Lob;

/**
 * @author george
 */
@Entity
@Data
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Comment("系统管理-默认设置-声道设置表")
public class ConfigChannelEntity extends BaseEntity {
    @Comment("双声道单选框，0禁用，1启用")
    private Integer doubleChannelRadio;
    @Comment("双声道客服默认声道, 0左声道，1右声道")
    private Integer doubleChannelDefault;
    @Comment("单声道单选框，0禁用，1启用")
    private Integer singleChannelRadio;
    @Lob
    @Comment("单声道音频客服身份标志语句")
    private String singleChannelText;
}
