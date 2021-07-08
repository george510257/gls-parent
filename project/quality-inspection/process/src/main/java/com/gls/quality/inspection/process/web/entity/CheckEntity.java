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
public class CheckEntity extends BaseEntity {
    private Integer id;
    private String applyCode;
    private String node;
    private String customerName;
    private String idCard;
    private String productName;
    private String companyName;
    private String checkType;
    private Timestamp createTime;
    private Byte status;
    private Long companyId;
}