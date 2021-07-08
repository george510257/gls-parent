package com.gls.quality.inspection.process.web.entity;

import com.gls.starter.data.jpa.annotations.Comment;
import com.gls.starter.data.jpa.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import java.sql.Timestamp;

/**
 * @author george
 */
@Entity
@Data
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Comment("客户端信息表")
public class ExtractCheckAudioEntity extends BaseEntity {
    private Integer id;
    private String customerServiceId;
    private Integer extractCheckId;
    private String dialogueId;
    private Byte status;
    private String audioUrl;
    private Long duration;
    private Integer dialogueNumber;
    private Byte resourceType;
    private Byte isTranslated;
    private Byte isLooked;
    private Integer serviceCheckRate;
    private Integer userCheckRate;
    private Integer violationCount;
    private Double recheckScore;
    private Double checkScore;
    private Double spotCheckScore;
    private Byte isChecked;
    private Byte isSpotChecked;
    private Timestamp checkTime;
    private Timestamp createTime;
    private Timestamp updateTime;
    private Byte isDeleted;
    private String customerMobile;
    private Byte isInvalid;
    private Integer recheckUserId;
    private String applyCode;
    private Integer distributeUserId;
    private Long companyId;
}