package com.gls.job.dashboard.web.entity;

import com.gls.starter.data.jpa.annotations.Comment;
import com.gls.starter.data.jpa.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.Entity;

/**
 * @author george
 */
@Entity
@Data
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Comment("用户信息表")
public class JobUserEntity extends BaseEntity {
    @Comment("账号")
    private String username;
    @Comment("密码")
    private String password;
    @Comment("角色：0-普通用户、1-管理员")
    private Integer role;
    @Comment("权限：执行器ID列表，多个逗号分割")
    private String permission;
}
