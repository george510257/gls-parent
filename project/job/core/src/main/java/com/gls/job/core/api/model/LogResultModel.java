package com.gls.job.core.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author george
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LogResultModel implements Serializable {

    private Integer fromLineNumber;
    private Integer toLineNumber;
    private String logContent;
    private Boolean endFlag;

}
