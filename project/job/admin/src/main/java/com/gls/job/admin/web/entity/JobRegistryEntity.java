package com.gls.job.admin.web.entity;

import com.gls.job.core.api.model.enums.RegistryType;
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
@Comment("注册信息表")
public class JobRegistryEntity extends BaseEntity {

    private RegistryType registryGroup;
    private String registryKey;
    private String registryValue;
}
