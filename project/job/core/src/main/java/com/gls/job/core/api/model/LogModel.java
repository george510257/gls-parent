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
public class LogModel implements Serializable {

    private long logDateTim;
    private long logId;
    private int fromLineNum;

}