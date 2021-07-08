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
public class UserEntity extends BaseEntity {
    private Integer id;
    private String username;
    private String passwordHash;
    private Byte status;
    private String portrait;
    private Byte userRole;
    private Integer userGroupingId;
    private String userGroupingIds;
    private String mobile;
    private String customerServiceId;
    private String realName;
    private Long companyId;
    private Integer createdUserId;
    private Byte isDeleted;
    private Timestamp createTime;
    private Timestamp updateTime;
    private Byte passwordStrength;
    private Byte isGrouped;
}
