package com.gls.quality.inspection.process.web.entity;

import com.gls.starter.data.jpa.annotations.Comment;
import com.gls.starter.data.jpa.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 * @author george
 */
@Entity
@Data
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Comment("用户表")
public class UserEntity extends BaseEntity {
    @Column(length = 50)
    @Comment("账号名")
    private String username;
    @Comment("密码")
    private String passwordHash;
    @Comment("状态 1:正常 2:禁用")
    private Integer status;
    @Column(length = 500)
    @Comment("头像")
    private String portrait;
    @Comment("用户角色 1.系统管理员  2.质检员 3.客服")
    private Integer userRole;
    @Comment("分组id")
    @ManyToOne
    private UserGroupingEntity userGrouping;
    @Column(length = 50)
    @Comment("分组id及其所有父级id 用于前端组件使用")
    private String userGroupingIds;
    @Column(length = 50)
    @Comment("手机号")
    private String mobile;
    @Comment("客服唯一标识")
    private String customerServiceNo;
    @Column(length = 50)
    @Comment("真实姓名")
    private String realName;
    @Comment("1:强，2：中等，3:弱")
    private Integer passwordStrength;
    @Comment(" 0:未分组  1:已分组")
    private Integer isGrouped;
}
