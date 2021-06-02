package com.gls.job.core.biz.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author xuxueli
 * @date 17/3/2
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HandleCallbackParam implements Serializable {

    private Long logId;
    private Date logDateTime;

    private Integer handleCode;
    private String handleMsg;

}
