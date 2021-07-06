package com.gls.job.admin.web.entity;

import com.gls.job.core.constants.GlueType;
import com.gls.starter.data.jpa.annotations.Comment;
import com.gls.starter.data.jpa.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;

/**
 * @author george
 */
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Accessors(chain = true)
@Comment("Glue版本信息表")
public class JobLogGlueEntity extends BaseEntity {
    @ManyToOne
    private JobInfoEntity jobInfo;
    @Comment("GLUE类型")
    @Enumerated(EnumType.STRING)
    private GlueType glueType;
    private String glueSource;
    private String glueRemark;
}
