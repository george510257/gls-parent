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
public class CorpusAudioTranforModel extends BaseModel {
    private CorpusModel corpus;
    private Integer lid;
    private Integer begin;
    private Integer end;
    private String onebest;
    private Double sc;
    private Integer spk;
}