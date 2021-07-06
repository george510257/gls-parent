package com.gls.job.core.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * @author xuxueli
 * @date 17/3/2
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class CallbackModel implements Serializable {
    private Long logId;
    private Date logDateTime;
    private Integer handleCode;
    private String handleMsg;
}
