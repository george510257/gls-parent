package com.gls.quality.inspection.process.web.entity;

import com.gls.starter.data.jpa.annotations.Comment;
import com.gls.starter.data.jpa.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.Date;

/**
 * @author george
 */
@Entity
@Data
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Comment("公司（租户）表")
public class CompanyEntity extends BaseEntity {
    @Comment("到期时间")
    private Date expireTime;
    @Comment("状态 1:正常 2:过期")
    private Integer status;
    @Column(length = 45)
    @Comment("公司英文名称")
    private String nameEn;
    @Column(length = 32)
    @Comment("审批人")
    private String approver;
    @Column(length = 32)
    @Comment("申请人")
    private String applicant;
    @Comment("公司类型，1-默认，2-越秀地产")
    private Integer type;
}
