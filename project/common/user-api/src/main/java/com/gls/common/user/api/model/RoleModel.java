package com.gls.common.user.api.model;

import com.gls.framework.api.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author george
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class RoleModel extends BaseModel {

    private String role;
}
