package com.gls.quality.inspection.process.web.entity;

import com.gls.starter.data.jpa.annotations.Comment;
import com.gls.starter.data.jpa.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

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
@Comment("客户端信息表")
public class SpotCheckEntity extends BaseEntity {
    private String spotCheckName;
    @ManyToOne
    private ExtractCheckEntity extractCheck;
    private Timestamp deadline;
    private Integer spotCheckSchedule;
    private Integer dialogueNumber;
    private Boolean isInvalided;
    private Boolean status;
}
