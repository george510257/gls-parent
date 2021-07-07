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
public class ConfigInvalidAudioModel extends BaseModel {
    private Boolean defineDurationRadio;
    private Integer defineDurationSecond;
    private Boolean defineResultRadio;
    private Boolean defineRoundRadio;
    private Integer defineRoundCount;
}