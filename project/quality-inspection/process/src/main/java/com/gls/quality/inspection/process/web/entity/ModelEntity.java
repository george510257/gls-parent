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
public class ModelEntity extends BaseEntity {
    private Integer id;
    private Long companyId;
    private String name;
    private Integer industryCategoryId;
    private String industryCategory;
    private Integer status;
    private Integer released;
    private Integer createBy;
    private Integer voiceTimeout;
    private Integer voiceShutdown;
    private Integer voiceFast;
    private Integer voiceDb;
    private Integer voiceInterrupt;
    private Byte voiceInterruptStatus;
    private Integer labelScore;
    private Timestamp createTime;
    private Timestamp updateTime;
    private Timestamp deleteTime;
    private Byte tokenModule;
    private Byte semanticsModule;
}
