package com.gls.quality.inspection.process.web.entity;

import com.gls.starter.data.jpa.annotations.Comment;
import com.gls.starter.data.jpa.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.sql.Timestamp;

/**
 * @author george
 */
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Accessors(chain = true)
@Comment("客户端信息表")
public class ExtractCheckAudioTextEntity extends BaseEntity {
    private String content;
    private String contentCorrect;
    private Integer checkRate;
    private Integer spk;
    private Boolean role;
    private Integer begin;
    private Integer end;
    @ManyToOne
    private ExtractCheckAudioEntity extractCheckAudio;
    private Timestamp excelTime;
}
