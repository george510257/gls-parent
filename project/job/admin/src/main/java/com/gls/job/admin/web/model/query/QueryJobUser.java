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
public class QueryJobUser {
    private String username;
    private Integer role;
}
