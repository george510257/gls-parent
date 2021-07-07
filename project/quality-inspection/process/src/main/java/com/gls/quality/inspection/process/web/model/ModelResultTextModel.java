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
public class ModelResultTextModel extends BaseModel {
    private ModelResultModel modelResult;
    private ExtractCheckAudioModel extractCheckAudio;
    private ExtractCheckAudioTextModel extractCheckAudioText;
    private String info;
}