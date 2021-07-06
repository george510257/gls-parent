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
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Accessors(chain = true)
@Comment("客户端信息表")
public class SectionAnnotatedEntity extends BaseEntity {
    @ManyToOne
    private CorpusEntity corpus;
    private String selection;
    private String totalLabel;
    private String singleLabel;
    private String complexLabel;
    private String extqRule;
}
