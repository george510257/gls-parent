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
public class SpecialLabelEntity extends BaseEntity {
    private Integer id;
    private Long companyId;
    private Integer modelId;
    private String name;
    private Integer type;
    private String callIds;
    private Integer status;
    private String childLabel;
    private String digest;
    private Timestamp createTime;
    private Timestamp updateTime;
    private Timestamp deleteTime;
}
