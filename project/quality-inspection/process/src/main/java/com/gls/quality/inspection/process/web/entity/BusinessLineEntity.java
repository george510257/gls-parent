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
public class BusinessLineEntity extends BaseEntity {
    private Integer id;
    private String belType;
    private Integer checkType;
    private String purpose;
    private String payType;
    private Integer modelId;
    private Integer count;
    private String remark;
    private Timestamp createTime;
    private Timestamp updateTime;
    private Long companyId;
}