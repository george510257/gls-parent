package com.gls.demo.security.web.model;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author george
 */
@Data
@Accessors(chain = true)
public class UserQueryConditionModel {
    private String username;
    private Integer age;
    private Integer ageTo;
    private String xxx;
}
