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
public class ExtractCheckAudioEntity extends BaseEntity {
    private String customerServiceId;
    private Integer extractCheckId;
    private String dialogueId;
    private Boolean status;
    private String audioUrl;
    private Long duration;
    private Integer dialogueNumber;
    private Boolean resourceType;
    private Boolean isTranslated;
    private Boolean isLooked;
    private Integer serviceCheckRate;
    private Integer userCheckRate;
    private Integer violationCount;
    private Double recheckScore;
    private Double checkScore;
    private Double spotCheckScore;
    private Boolean isChecked;
    private Boolean isSpotChecked;
    private Timestamp checkTime;
    private String customerMobile;
    private Boolean isInvalid;
    private Integer recheckUserId;
    private String applyCode;
    private Integer distributeUserId;
}
