package com.gls.common.user.api.model;

import com.gls.framework.api.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author george
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class RoleModel extends BaseModel {
    private String role;
}
