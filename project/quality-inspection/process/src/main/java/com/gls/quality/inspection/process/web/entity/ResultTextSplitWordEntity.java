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
@Comment("")
public class ResultTextSplitWordEntity extends BaseEntity {
    @Comment("出现的次数")
    private Integer number;
    @Comment("通话id")
    @ManyToOne
    private ExtractCheckAudioEntity extractCheckAudio;
    @Comment("词语名称")
    private String word;
}
