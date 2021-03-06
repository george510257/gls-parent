package com.gls.quality.inspection.process.web.entity;

import com.gls.starter.data.jpa.annotations.Comment;
import com.gls.starter.data.jpa.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.Column;
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
@Comment("模型会话转写结果")
public class CorpusAudioTranforEntity extends BaseEntity {
    @Comment("语料id")
    @ManyToOne
    private CorpusEntity corpus;
    @Comment("句子id")
    private Integer lid;
    @Comment("开始时间")
    private Integer begin;
    @Comment("结束时间")
    private Integer end;
    @Column(length = 5000)
    @Comment("句子")
    private String onebest;
    @Comment("分数")
    private Double sc;
    @Comment("角色 0.坐席 1.客户")
    private Integer spk;
}
