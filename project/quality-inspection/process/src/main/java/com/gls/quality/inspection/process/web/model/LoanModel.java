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
public class LoanModel extends BaseModel {
    private CheckModel check;
    private String telNumber;
    private Date checkTime;
    private String checkNo;
    private Date endTime;
    private String faceMess;
    private String operateUser;
    private String operateName;
    private String brIdPar;
    private String brId;
    private String groupId;
    private String groupName;
    private String dhSpResult;
    private String fileUrl;
    private Boolean status;
    private String belType;
    private String privateEmployer;
    private String purpose;
    private String payType;
    private String scoreGrade;
    private String carQualification;
    private String houseQualification;
    private String policyQualification;
}