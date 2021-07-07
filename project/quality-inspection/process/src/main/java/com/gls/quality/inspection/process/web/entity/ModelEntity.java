package com.gls.quality.inspection.process.web.entity;

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
@Comment("客户端信息表")
public class ModelEntity extends BaseEntity {
    @ManyToOne
    private IndustryCategoryEntity industryCategory;
    private Integer status;
    private Integer released;
    private Integer createBy;
    private Integer voiceTimeout;
    private Integer voiceShutdown;
    private Integer voiceFast;
    private Integer voiceDb;
    private Integer voiceInterrupt;
    private Boolean voiceInterruptStatus;
    private Integer labelScore;
    private Boolean tokenModule;
    private Boolean semanticsModule;
}