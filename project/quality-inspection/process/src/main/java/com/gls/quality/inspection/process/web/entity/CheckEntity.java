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
public class CheckEntity extends BaseEntity {
    private String applyCode;
    private String node;
    private String customerName;
    private String idCard;
    private String productName;
    private String companyName;
    private String checkType;
    private Boolean status;
}
