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
@Comment("")
public class ResultTextSplitWordEntity extends BaseEntity {
    @Column
    @Comment("出现的次数")
    private Integer number;
    @Column
    @Comment("通话id")
    private Integer extractCheckAudioId;
    @Column
    @Comment("词语名称")
    private String word;
}
