package com.gls.job.core.biz.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author xuxueli 2020-04-11 22:27
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LogParam implements Serializable {

    private Date logDateTime;
    private Long logId;
    private Integer fromLineNum;

}