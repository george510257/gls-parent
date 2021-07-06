package com.gls.job.admin.web.model.query;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author georg
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class QueryJobLog {
    private Long jobGroupId;
    private Long jobInfoId;
    private Date triggerTimeFrom;
    private Date triggerTimeTo;
    private Integer logStatus;
}
