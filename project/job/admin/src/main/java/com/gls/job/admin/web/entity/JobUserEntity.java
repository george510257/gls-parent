package com.gls.job.admin.web.entity;

import com.gls.starter.data.jpa.annotations.Comment;
import com.gls.starter.data.jpa.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.List;

/**
 * @author george
 */
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Comment("用户信息表")
public class JobUserEntity extends BaseEntity {
    @Comment("账号")
    private String username;
    @Comment("密码")
    private String password;
    @Comment("角色：0-普通用户、1-管理员")
    private Integer role;
    @Comment("权限：执行器ID列表")
    @ManyToMany
    private List<JobGroupEntity> jobGroups;
}
