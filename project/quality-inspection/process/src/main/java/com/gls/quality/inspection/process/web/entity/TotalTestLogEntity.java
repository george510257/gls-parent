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
public class TotalTestLogEntity extends BaseEntity {
    private Integer totalTestTaskId;
    private String testContent;
    private String expectLabel;
    private Boolean expectRole;
    private String recLabel;
    private String recRule;
    private Boolean recRole;
    private String module;
    private Double score;
    private Integer pass;
}
