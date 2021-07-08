package com.gls.job.dashboard.web.entity;

import com.gls.starter.data.jpa.annotations.Comment;
import com.gls.starter.data.jpa.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

/**
 * @author george
 */
@Entity
@Data
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Comment("执行器信息表")
public class JobGroupEntity extends BaseEntity {
    @Comment("执行器AppName")
    private String appName;
    @Comment("执行器名称")
    private String title;
    @Comment("执行器地址类型：0=自动注册、1=手动录入")
    private Boolean addressType;
    @Comment("执行器地址列表，多地址逗号分隔")
    @ElementCollection
    private List<String> addresses;
    @Comment("执行器地址列表(系统注册)")
    @OneToMany(mappedBy = "jobGroup")
    private List<JobRegistryEntity> jobRegistries;
}
