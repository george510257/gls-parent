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
public class SpotCheckEntity extends BaseEntity {
    private String spotCheckName;
    @ManyToOne
    private ExtractCheckEntity extractCheck;
    private Date deadline;
    private Integer spotCheckSchedule;
    private Integer dialogueNumber;
    private Boolean isInvalided;
    private Boolean status;
}
