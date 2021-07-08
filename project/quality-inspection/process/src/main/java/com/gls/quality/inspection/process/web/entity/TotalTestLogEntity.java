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
public class TotalTestLogEntity extends BaseEntity {
    private Integer id;
    private Integer totalTestTaskId;
    private String testContent;
    private String expectLabel;
    private Byte expectRole;
    private String recLabel;
    private String recRule;
    private Byte recRole;
    private String module;
    private Double score;
    private Integer pass;
    private Timestamp createTime;
    private Long companyId;
}
