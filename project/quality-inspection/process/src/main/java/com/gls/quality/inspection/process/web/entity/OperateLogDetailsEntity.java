package com.gls.quality.inspection.process.web.entity;

import com.gls.starter.data.jpa.annotations.Comment;
import com.gls.starter.data.jpa.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * @author george
 */
@Entity
@Data
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Comment("操作日志")
public class OperateLogDetailsEntity extends BaseEntity {
    @Column(length = 32)
    @Comment("模块")
    private String module;
    @Column(length = 32)
    @Comment("操作名称")
    private String operateName;
    @Column(length = 1000)
    @Comment("记录的具体内容")
    private String logDetails;
    @Column(length = 32)
    @Comment("账号名")
    private String username;
    @Column(length = 32)
    @Comment("真实姓名")
    private String realname;
}
