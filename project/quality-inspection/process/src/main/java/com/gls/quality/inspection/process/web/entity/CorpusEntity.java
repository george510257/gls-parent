package com.gls.quality.inspection.process.web.entity;

import com.gls.starter.data.jpa.annotations.Comment;
import com.gls.starter.data.jpa.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 * @author george
 */
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Comment("客户端信息表")
public class CorpusEntity extends BaseEntity {
    @ManyToOne
    private ModelEntity model;
    private String content;
    private String audioUrl;
    private Boolean translateStatus;
    private Integer status;
    private Integer isAnnotated;
    private Integer audioLen;
    private Integer totalLabelId;
    private Integer complexLabelId;
    private String semanticLabelList;
}
