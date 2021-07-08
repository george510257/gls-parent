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
public class ExtractCheckResultEntity extends BaseEntity {
    private Integer id;
    private Integer extractCheckAudioId;
    private String violationsItem;
    private Double violationsScore;
    private String category;
    private String paragraph;
    private Integer scoreItemId;
    private Integer scoreItemType;
    private Byte scoreAttribute;
    private Byte inspectionObject;
    private String keyInfo;
    private Byte type;
    private Byte recheckStatus;
    private String representation;
    private Byte isMissed;
    private Timestamp createTime;
    private Timestamp updateTime;
    private Byte isDeleted;
    private Byte isError;
    private Long companyId;
    private String scoreStrategy;
}