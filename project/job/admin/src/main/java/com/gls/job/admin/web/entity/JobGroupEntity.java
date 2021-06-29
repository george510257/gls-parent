package com.gls.job.admin.web.entity;

import com.gls.starter.data.jpa.annotations.Comment;
import com.gls.starter.data.jpa.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

/**
 * @author george
 */
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Comment("执行器信息表")
public class JobGroupEntity extends BaseEntity {
    private String appname;
    private String title;
    @Comment("执行器地址类型：true=自动注册、false=手动录入")
    private Boolean addressType;
    @Comment("执行器地址列表(手动录入)")
    @ElementCollection
    private List<String> addressList;
    @Comment("执行器地址列表(系统注册)")
    @OneToMany(mappedBy = "jobGroup")
    private List<JobRegistryEntity> jobRegistries;
}
