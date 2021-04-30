package com.gls.job.core.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author george
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CallbackModel implements Serializable {

    private Long logId;
    private Date logDateTime;

    private Integer handleCode;
    private String handleMsg;

}
