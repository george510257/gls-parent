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

    private int fromLineNum;
    private int toLineNum;
    private String logContent;
    private boolean isEnd;

}
