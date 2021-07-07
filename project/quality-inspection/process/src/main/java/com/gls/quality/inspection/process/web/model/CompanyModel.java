package com.gls.quality.inspection.process.web.model;

import com.gls.framework.api.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author george
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class CompanyModel extends BaseModel {
    private Date expireTime;
    private Boolean status;
    private String nameEn;
    private String approver;
    private String applicant;
    private Integer type;
}