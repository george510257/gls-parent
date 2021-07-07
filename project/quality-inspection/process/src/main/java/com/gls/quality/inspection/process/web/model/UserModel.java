package com.gls.quality.inspection.process.web.model;

import com.gls.framework.api.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author george
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class UserModel extends BaseModel {
    private String username;
    private String passwordHash;
    private Boolean status;
    private String portrait;
    private Boolean userRole;
    private Integer userGroupingId;
    private String userGroupingIds;
    private String mobile;
    private String customerServiceId;
    private String realName;
    private Boolean passwordStrength;
    private Boolean isGrouped;
}