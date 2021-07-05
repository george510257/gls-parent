package com.gls.quality.inspection.process.web.entity;

import com.gls.starter.data.jpa.annotations.Comment;
import com.gls.starter.data.jpa.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Entity;
import java.sql.Timestamp;

/**
 * @author george
 */
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Comment("客户端信息表")
public class ExtractCheckAudioTextEntity extends BaseEntity {
    private String content;
    private String contentCorrect;
    private Integer checkRate;
    private Integer spk;
    private Boolean role;
    private Integer begin;
    private Integer end;
    private Integer extractCheckAudioId;
    private Timestamp excelTime;
}
