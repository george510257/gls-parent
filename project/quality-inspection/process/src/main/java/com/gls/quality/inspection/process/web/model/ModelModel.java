package com.gls.quality.inspection.process.web.model;

import com.gls.framework.api.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author george
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class ModelModel extends BaseModel {
    private IndustryCategoryModel industryCategory;
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