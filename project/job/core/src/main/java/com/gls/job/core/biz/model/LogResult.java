package com.gls.job.core.biz.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author xuxueli
 * @date 17/3/23
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LogResult implements Serializable {

    private Integer fromLineNum;
    private Integer toLineNum;
    private String logContent;
    private Boolean endFlag;

}
