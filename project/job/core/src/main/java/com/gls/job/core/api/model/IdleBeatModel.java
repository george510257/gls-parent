package com.gls.job.core.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author xuxueli 2020-04-11 22:27
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class IdleBeatModel implements Serializable {
    private Long jobId;
}