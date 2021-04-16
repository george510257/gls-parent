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
public class CallbackModel implements Serializable {

    private long logId;
    private long logDateTim;

    private int handleCode;
    private String handleMsg;

}
