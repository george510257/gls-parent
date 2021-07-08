package com.gls.quality.inspection.process.web.entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "tb_loan", schema = "ai_quality_check", catalog = "")
public class LoanEntity {
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

    @Id
    @Column(name = "id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "check_id")
    public Integer getCheckId() {
        return checkId;
    }

    public void setCheckId(Integer checkId) {
        this.checkId = checkId;
    }

    @Basic
    @Column(name = "tel_number")
    public String getTelNumber() {
        return telNumber;
    }

    public void setTelNumber(String telNumber) {
        this.telNumber = telNumber;
    }

    @Basic
    @Column(name = "check_time")
    public Timestamp getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(Timestamp checkTime) {
        this.checkTime = checkTime;
    }

    @Basic
    @Column(name = "check_no")
    public String getCheckNo() {
        return checkNo;
    }

    public void setCheckNo(String checkNo) {
        this.checkNo = checkNo;
    }

    @Basic
    @Column(name = "end_time")
    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    @Basic
    @Column(name = "face_mess")
    public String getFaceMess() {
        return faceMess;
    }

    public void setFaceMess(String faceMess) {
        this.faceMess = faceMess;
    }

    @Basic
    @Column(name = "operate_user")
    public String getOperateUser() {
        return operateUser;
    }

    public void setOperateUser(String operateUser) {
        this.operateUser = operateUser;
    }

    @Basic
    @Column(name = "operate_name")
    public String getOperateName() {
        return operateName;
    }

    public void setOperateName(String operateName) {
        this.operateName = operateName;
    }

    @Basic
    @Column(name = "br_id_par")
    public String getBrIdPar() {
        return brIdPar;
    }

    public void setBrIdPar(String brIdPar) {
        this.brIdPar = brIdPar;
    }

    @Basic
    @Column(name = "br_id")
    public String getBrId() {
        return brId;
    }

    public void setBrId(String brId) {
        this.brId = brId;
    }

    @Basic
    @Column(name = "group_id")
    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    @Basic
    @Column(name = "group_name")
    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    @Basic
    @Column(name = "dh_sp_result")
    public String getDhSpResult() {
        return dhSpResult;
    }

    public void setDhSpResult(String dhSpResult) {
        this.dhSpResult = dhSpResult;
    }

    @Basic
    @Column(name = "file_url")
    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    @Basic
    @Column(name = "create_time")
    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    @Basic
    @Column(name = "status")
    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    @Basic
    @Column(name = "bel_type")
    public String getBelType() {
        return belType;
    }

    public void setBelType(String belType) {
        this.belType = belType;
    }

    @Basic
    @Column(name = "private_employer")
    public String getPrivateEmployer() {
        return privateEmployer;
    }

    public void setPrivateEmployer(String privateEmployer) {
        this.privateEmployer = privateEmployer;
    }

    @Basic
    @Column(name = "purpose")
    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    @Basic
    @Column(name = "pay_type")
    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    @Basic
    @Column(name = "score_grade")
    public String getScoreGrade() {
        return scoreGrade;
    }

    public void setScoreGrade(String scoreGrade) {
        this.scoreGrade = scoreGrade;
    }

    @Basic
    @Column(name = "car_qualification")
    public String getCarQualification() {
        return carQualification;
    }

    public void setCarQualification(String carQualification) {
        this.carQualification = carQualification;
    }

    @Basic
    @Column(name = "house_qualification")
    public String getHouseQualification() {
        return houseQualification;
    }

    public void setHouseQualification(String houseQualification) {
        this.houseQualification = houseQualification;
    }

    @Basic
    @Column(name = "policy_qualification")
    public String getPolicyQualification() {
        return policyQualification;
    }

    public void setPolicyQualification(String policyQualification) {
        this.policyQualification = policyQualification;
    }

    @Basic
    @Column(name = "company_id")
    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (checkId != null ? checkId.hashCode() : 0);
        result = 31 * result + (telNumber != null ? telNumber.hashCode() : 0);
        result = 31 * result + (checkTime != null ? checkTime.hashCode() : 0);
        result = 31 * result + (checkNo != null ? checkNo.hashCode() : 0);
        result = 31 * result + (endTime != null ? endTime.hashCode() : 0);
        result = 31 * result + (faceMess != null ? faceMess.hashCode() : 0);
        result = 31 * result + (operateUser != null ? operateUser.hashCode() : 0);
        result = 31 * result + (operateName != null ? operateName.hashCode() : 0);
        result = 31 * result + (brIdPar != null ? brIdPar.hashCode() : 0);
        result = 31 * result + (brId != null ? brId.hashCode() : 0);
        result = 31 * result + (groupId != null ? groupId.hashCode() : 0);
        result = 31 * result + (groupName != null ? groupName.hashCode() : 0);
        result = 31 * result + (dhSpResult != null ? dhSpResult.hashCode() : 0);
        result = 31 * result + (fileUrl != null ? fileUrl.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (belType != null ? belType.hashCode() : 0);
        result = 31 * result + (privateEmployer != null ? privateEmployer.hashCode() : 0);
        result = 31 * result + (purpose != null ? purpose.hashCode() : 0);
        result = 31 * result + (payType != null ? payType.hashCode() : 0);
        result = 31 * result + (scoreGrade != null ? scoreGrade.hashCode() : 0);
        result = 31 * result + (carQualification != null ? carQualification.hashCode() : 0);
        result = 31 * result + (houseQualification != null ? houseQualification.hashCode() : 0);
        result = 31 * result + (policyQualification != null ? policyQualification.hashCode() : 0);
        result = 31 * result + (companyId != null ? companyId.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LoanEntity that = (LoanEntity) o;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (checkId != null ? !checkId.equals(that.checkId) : that.checkId != null) return false;
        if (telNumber != null ? !telNumber.equals(that.telNumber) : that.telNumber != null) return false;
        if (checkTime != null ? !checkTime.equals(that.checkTime) : that.checkTime != null) return false;
        if (checkNo != null ? !checkNo.equals(that.checkNo) : that.checkNo != null) return false;
        if (endTime != null ? !endTime.equals(that.endTime) : that.endTime != null) return false;
        if (faceMess != null ? !faceMess.equals(that.faceMess) : that.faceMess != null) return false;
        if (operateUser != null ? !operateUser.equals(that.operateUser) : that.operateUser != null) return false;
        if (operateName != null ? !operateName.equals(that.operateName) : that.operateName != null) return false;
        if (brIdPar != null ? !brIdPar.equals(that.brIdPar) : that.brIdPar != null) return false;
        if (brId != null ? !brId.equals(that.brId) : that.brId != null) return false;
        if (groupId != null ? !groupId.equals(that.groupId) : that.groupId != null) return false;
        if (groupName != null ? !groupName.equals(that.groupName) : that.groupName != null) return false;
        if (dhSpResult != null ? !dhSpResult.equals(that.dhSpResult) : that.dhSpResult != null) return false;
        if (fileUrl != null ? !fileUrl.equals(that.fileUrl) : that.fileUrl != null) return false;
        if (createTime != null ? !createTime.equals(that.createTime) : that.createTime != null) return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;
        if (belType != null ? !belType.equals(that.belType) : that.belType != null) return false;
        if (privateEmployer != null ? !privateEmployer.equals(that.privateEmployer) : that.privateEmployer != null)
            return false;
        if (purpose != null ? !purpose.equals(that.purpose) : that.purpose != null) return false;
        if (payType != null ? !payType.equals(that.payType) : that.payType != null) return false;
        if (scoreGrade != null ? !scoreGrade.equals(that.scoreGrade) : that.scoreGrade != null) return false;
        if (carQualification != null ? !carQualification.equals(that.carQualification) : that.carQualification != null)
            return false;
        if (houseQualification != null ? !houseQualification.equals(that.houseQualification) : that.houseQualification != null)
            return false;
        if (policyQualification != null ? !policyQualification.equals(that.policyQualification) : that.policyQualification != null)
            return false;
        if (companyId != null ? !companyId.equals(that.companyId) : that.companyId != null) return false;
        return true;
    }
}
