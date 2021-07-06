package com.gls.job.admin.web.entity;

import com.gls.job.core.constants.RegistryType;
import com.gls.starter.data.jpa.annotations.Comment;
import com.gls.starter.data.jpa.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 * @author george
 */
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Accessors(chain = true)
@Comment("注册信息表")
public class JobRegistryEntity extends BaseEntity {
    @ManyToOne
    private JobGroupEntity jobGroup;
    private RegistryType registryGroup;
    private String registryKey;
    private String registryValue;
}
