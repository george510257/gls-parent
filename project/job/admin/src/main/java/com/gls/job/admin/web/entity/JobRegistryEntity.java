package com.gls.job.admin.web.entity;

import com.gls.job.core.enums.RegistryType;
import com.gls.starter.data.jpa.annotations.Comment;
import com.gls.starter.data.jpa.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author xuxueli
 * @date 16/9/30
 */
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "job_registry")
@Comment("执行注册信息表")
public class JobRegistryEntity extends BaseEntity {

    private RegistryType registryType;
    private String registryKey;
    private String registryValue;

}
