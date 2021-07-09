package com.gls.quality.inspection.process.web.entity;

import com.gls.starter.data.jpa.annotations.Comment;
import com.gls.starter.data.jpa.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.Date;

/**
 * @author george
 */
@Entity
@Data
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Comment("语音翻译文本")
public class ExtractCheckAudioTextEntity extends BaseEntity {
    @Column(length = 65535)
    @Comment("一句话的最佳内容文本")
    private String content;
    @Column(length = 65535)
    @Comment("修改后的文本")
    private String contentCorrect;
    @Comment("单句文本的转写准确率")
    private Integer checkRate;
    @Comment("说话人序号")
    private Integer spk;
    @Comment("说话人角色  1客服 2顾客")
    private Integer role;
    @Comment("句子开始（帧）")
    private Integer begin;
    @Comment("句子结束（帧）")
    private Integer end;
    @Comment("语音表主键")
    private Long extractCheckAudioId;
    @Comment("excel文本时间")
    private Date excelTime;
}
