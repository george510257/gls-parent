package com.gls.common.user.web.entity;

import com.gls.starter.data.jpa.annotations.Comment;
import com.gls.starter.data.jpa.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.List;

/**
 * @author george
 */
@Entity
@Data
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Comment("用户信息表")
public class UserEntity extends BaseEntity {
    @Column(unique = true)
    @Comment("用户名")
    private String username;
    @Comment("密码")
    private String password;
    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    @Comment("角色列表")
    private List<RoleEntity> roles;
    @Comment("账号过期标识 true:未过期; false:已过期")
    private Boolean accountNonExpired;
    @Comment("账号锁定标识 true:未锁定; false:已锁定")
    private Boolean accountNonLocked;
    @Comment("认证过期标识 true:未过期; false:已过期")
    private Boolean credentialsNonExpired;
    @Comment("启用标识 true:已启用; false:未启用")
    private Boolean enabled;
}
