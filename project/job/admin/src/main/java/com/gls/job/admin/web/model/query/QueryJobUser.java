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
public class QueryJobUser {
    private String username;
    private Integer role;
}
