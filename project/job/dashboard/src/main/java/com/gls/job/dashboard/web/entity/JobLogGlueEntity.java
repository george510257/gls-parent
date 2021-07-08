package com.gls.job.dashboard.web.entity;

import com.gls.job.core.constants.GlueType;
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
@Entity
@Data
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Comment("GLUE源码信息表")
public class JobLogGlueEntity extends BaseEntity {
    @Comment("任务，主键ID")
    @ManyToOne
    private JobInfoEntity jobInfo;
    @Comment("GLUE类型")
    private GlueType glueType;
    @Comment("GLUE源代码")
    private String glueSource;
    @Comment("GLUE备注")
    private String glueRemark;
}
