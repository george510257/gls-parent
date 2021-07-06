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
public class ExtractCheckResultEntity extends BaseEntity {
    @ManyToOne
    private ExtractCheckAudioEntity extractCheckAudio;
    private String violationsItem;
    private Double violationsScore;
    private String category;
    private String paragraph;
    @ManyToOne
    private ScoreItemsEntity scoreItems;
    private Integer scoreItemType;
    private Boolean scoreAttribute;
    private Boolean inspectionObject;
    private String keyInfo;
    private Boolean type;
    private Boolean recheckStatus;
    private String representation;
    private Boolean isMissed;
    private Boolean isError;
    private String scoreStrategy;
}
