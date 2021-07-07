package com.gls.quality.inspection.process.web.entity;

import com.gls.starter.data.jpa.annotations.Comment;
import com.gls.starter.data.jpa.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.util.Date;

/**
 * @author george
 */
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Accessors(chain = true)
@Comment("客户端信息表")
public class ExtractCheckAudioEntity extends BaseEntity {
    private String customerServiceId;
    @ManyToOne
    private ExtractCheckEntity extractCheck;
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
    private Date checkTime;
    private String customerMobile;
    private Boolean isInvalid;
    private Integer recheckUserId;
    private String applyCode;
    private Integer distributeUserId;
}
