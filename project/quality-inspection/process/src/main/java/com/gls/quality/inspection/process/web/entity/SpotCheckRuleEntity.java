package com.gls.quality.inspection.process.web.entity;

import com.gls.starter.data.jpa.annotations.Comment;
import com.gls.starter.data.jpa.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 * @author george
 */
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Comment("客户端信息表")
public class SpotCheckRuleEntity extends BaseEntity {
    @ManyToOne
    private SpotCheckEntity spotCheck;
    private Integer spotCheckNumber;
    private Boolean ruleType;
    @ManyToOne
    private ScoreItemsEntity scoreItems;
    private Boolean scoreItemType;
}
