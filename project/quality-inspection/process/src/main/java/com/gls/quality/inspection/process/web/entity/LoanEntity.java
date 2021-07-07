package com.gls.quality.inspection.process.web.entity;

import com.gls.starter.data.jpa.annotations.Comment;
import com.gls.starter.data.jpa.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.util.Date;

/**
 * @author george
 */
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Accessors(chain = true)
@Comment("客户端信息表")
public class LoanEntity extends BaseEntity {
    @ManyToOne
    private CheckEntity check;
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
