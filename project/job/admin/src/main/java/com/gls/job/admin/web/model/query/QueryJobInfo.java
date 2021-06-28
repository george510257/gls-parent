package com.gls.job.admin.web.model.query;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author george
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QueryJobInfo {
    private Long jobGroupId;
    private Boolean triggerStatus;
    private String jobDesc;
    private String executorHandler;
    private String author;
}
