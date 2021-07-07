package com.gls.quality.inspection.process.web.model;

import com.gls.framework.api.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author george
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class ExtractCheckAudioTextModel extends BaseModel {
    private String content;
    private String contentCorrect;
    private Integer checkRate;
    private Integer spk;
    private Boolean role;
    private Integer begin;
    private Integer end;
    private ExtractCheckAudioModel extractCheckAudio;
    private Date excelTime;
}