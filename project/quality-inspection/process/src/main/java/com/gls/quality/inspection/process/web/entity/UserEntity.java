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
public class UserEntity extends BaseEntity {
    private String username;
    private String passwordHash;
    private Boolean status;
    private String portrait;
    private Boolean userRole;
    private Integer userGroupingId;
    private String userGroupingIds;
    private String mobile;
    private String customerServiceId;
    private String realName;
    private Boolean passwordStrength;
    private Boolean isGrouped;
}
