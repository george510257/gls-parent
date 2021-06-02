package com.gls.job.admin.web.entity;

import com.gls.starter.data.jpa.annotations.Comment;
import com.gls.starter.data.jpa.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.List;

/**
 * @author xuxueli 2019-05-04 16:43:12
 */
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "job_user")
@Comment("用户信息表")
public class JobUserEntity extends BaseEntity {

    @Comment("账号")
    private String username;
    @Comment("密码")
    private String password;
    @Comment("角色：0-普通用户、1-管理员")
    private Integer role;

    @Comment("权限：执行器ID列表，多个逗号分割")
    @ManyToMany
    private List<JobInfoEntity> permission;

}
