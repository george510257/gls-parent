package com.gls.job.admin.web.entity;

import com.gls.job.core.enums.GlueType;
import com.gls.starter.data.jpa.annotations.Comment;
import com.gls.starter.data.jpa.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;

/**
 * gls-job log for glue, used to track job code process
 *
 * @author xuxueli 2016-5-19 17:57:46
 */
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "job_log_glue")
@Comment("执行日志Glue信息表")
public class JobLogGlueEntity extends BaseEntity {

    @ManyToOne
    private JobInfoEntity jobInfo;

    @Comment("GLUE类型")
    @Enumerated(EnumType.STRING)
    private GlueType glueType;

    private String glueSource;

    private String glueRemark;

}
