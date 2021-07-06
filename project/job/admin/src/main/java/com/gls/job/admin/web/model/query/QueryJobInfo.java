package com.gls.job.admin.web.model.query;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author george
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class QueryJobInfo {
    private Long jobGroupId;
    private Boolean triggerStatus;
    private String jobDesc;
    private String executorHandler;
    private String author;
}
