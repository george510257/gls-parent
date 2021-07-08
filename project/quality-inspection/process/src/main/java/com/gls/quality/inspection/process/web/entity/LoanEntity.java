package com.gls.quality.inspection.process.web.entity;

import com.gls.starter.data.jpa.annotations.Comment;
import com.gls.starter.data.jpa.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import java.sql.Timestamp;

/**
 * @author george
 */
@Entity
@Data
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Comment("客户端信息表")
public class LoanEntity extends BaseEntity {
    private Integer id;
    private Integer checkId;
    private String telNumber;
    private Timestamp checkTime;
    private String checkNo;
    private Timestamp endTime;
    private String faceMess;
    private String operateUser;
    private String operateName;
    private String brIdPar;
    private String brId;
    private String groupId;
    private String groupName;
    private String dhSpResult;
    private String fileUrl;
    private Timestamp createTime;
    private Byte status;
    private String belType;
    private String privateEmployer;
    private String purpose;
    private String payType;
    private String scoreGrade;
    private String carQualification;
    private String houseQualification;
    private String policyQualification;
    private Long companyId;
}
