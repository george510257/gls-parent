package com.gls.quality.inspection.process.web.entity;

import com.gls.starter.data.jpa.annotations.Comment;
import com.gls.starter.data.jpa.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import java.sql.Timestamp;

/**
 * @author george
 */
@Entity
@Data
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Comment("客户端信息表")
public class ConfigChannelEntity extends BaseEntity {
    private Integer id;
    private Byte doubleChannelRadio;
    private Byte doubleChannelDefault;
    private Byte singleChannelRadio;
    private String singleChannelText;
    private Timestamp createTime;
    private Timestamp updateTime;
    private Long companyId;
}