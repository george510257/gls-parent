package com.gls.job.core.biz.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author xuxueli 2020-04-11 22:27
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IdleBeatParam implements Serializable {

    private Long jobId;
}