package com.gls.quality.inspection.process.web.entity;

import com.gls.starter.data.jpa.annotations.Comment;
import com.gls.starter.data.jpa.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Entity;

/**
 * @author george
 */
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Comment("客户端信息表")
public class ExtractCheckResultEntity extends BaseEntity {
    private Integer extractCheckAudioId;
    private String violationsItem;
    private Double violationsScore;
    private String category;
    private String paragraph;
    private Integer scoreItemId;
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
