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
public class CompanyEntity extends BaseEntity {
    private Long id;
    private Timestamp createTime;
    private Timestamp updateTime;
    private Timestamp expireTime;
    private Byte status;
    private Timestamp deleteTime;
    private String nameEn;
    private String name;
    private String approver;
    private String applicant;
    private Integer type;
}