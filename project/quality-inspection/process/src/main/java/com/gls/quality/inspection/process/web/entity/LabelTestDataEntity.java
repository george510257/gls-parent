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
@Comment("")
public class LabelTestDataEntity extends BaseEntity {
    @Comment("模型id")
    private Long modelId;
    @Comment("状态")
    private Integer status;
    @Comment("整通通过率")
    private Float totalLabelPassingRate;
    @Comment("语音检测通过率")
    private Float voiceCheckPassingRate;
    @Comment("语义标签通过率")
    private Float semanticLabelPassingRate;
    @Comment("符合标签通过率")
    private Float complexLabelPassingRate;
}
