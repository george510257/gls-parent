package com.gls.quality.inspection.process.web.entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "tb_extract_check_audio", schema = "ai_quality_check", catalog = "")
public class ExtractCheckAudioEntity {
    private Integer id;
    private String customerServiceId;
    private Integer extractCheckId;
    private String dialogueId;
    private Byte status;
    private String audioUrl;
    private Long duration;
    private Integer dialogueNumber;
    private Byte resourceType;
    private Byte isTranslated;
    private Byte isLooked;
    private Integer serviceCheckRate;
    private Integer userCheckRate;
    private Integer violationCount;
    private Double recheckScore;
    private Double checkScore;
    private Double spotCheckScore;
    private Byte isChecked;
    private Byte isSpotChecked;
    private Timestamp checkTime;
    private Timestamp createTime;
    private Timestamp updateTime;
    private Byte isDeleted;
    private String customerMobile;
    private Byte isInvalid;
    private Integer recheckUserId;
    private String applyCode;
    private Integer distributeUserId;
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
    @Column(name = "customer_service_id")
    public String getCustomerServiceId() {
        return customerServiceId;
    }

    public void setCustomerServiceId(String customerServiceId) {
        this.customerServiceId = customerServiceId;
    }

    @Basic
    @Column(name = "extract_check_id")
    public Integer getExtractCheckId() {
        return extractCheckId;
    }

    public void setExtractCheckId(Integer extractCheckId) {
        this.extractCheckId = extractCheckId;
    }

    @Basic
    @Column(name = "dialogue_id")
    public String getDialogueId() {
        return dialogueId;
    }

    public void setDialogueId(String dialogueId) {
        this.dialogueId = dialogueId;
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
    @Column(name = "audio_url")
    public String getAudioUrl() {
        return audioUrl;
    }

    public void setAudioUrl(String audioUrl) {
        this.audioUrl = audioUrl;
    }

    @Basic
    @Column(name = "duration")
    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    @Basic
    @Column(name = "dialogue_number")
    public Integer getDialogueNumber() {
        return dialogueNumber;
    }

    public void setDialogueNumber(Integer dialogueNumber) {
        this.dialogueNumber = dialogueNumber;
    }

    @Basic
    @Column(name = "resource_type")
    public Byte getResourceType() {
        return resourceType;
    }

    public void setResourceType(Byte resourceType) {
        this.resourceType = resourceType;
    }

    @Basic
    @Column(name = "is_translated")
    public Byte getIsTranslated() {
        return isTranslated;
    }

    public void setIsTranslated(Byte isTranslated) {
        this.isTranslated = isTranslated;
    }

    @Basic
    @Column(name = "is_looked")
    public Byte getIsLooked() {
        return isLooked;
    }

    public void setIsLooked(Byte isLooked) {
        this.isLooked = isLooked;
    }

    @Basic
    @Column(name = "service_check_rate")
    public Integer getServiceCheckRate() {
        return serviceCheckRate;
    }

    public void setServiceCheckRate(Integer serviceCheckRate) {
        this.serviceCheckRate = serviceCheckRate;
    }

    @Basic
    @Column(name = "user_check_rate")
    public Integer getUserCheckRate() {
        return userCheckRate;
    }

    public void setUserCheckRate(Integer userCheckRate) {
        this.userCheckRate = userCheckRate;
    }

    @Basic
    @Column(name = "violation_count")
    public Integer getViolationCount() {
        return violationCount;
    }

    public void setViolationCount(Integer violationCount) {
        this.violationCount = violationCount;
    }

    @Basic
    @Column(name = "recheck_score")
    public Double getRecheckScore() {
        return recheckScore;
    }

    public void setRecheckScore(Double recheckScore) {
        this.recheckScore = recheckScore;
    }

    @Basic
    @Column(name = "check_score")
    public Double getCheckScore() {
        return checkScore;
    }

    public void setCheckScore(Double checkScore) {
        this.checkScore = checkScore;
    }

    @Basic
    @Column(name = "spot_check_score")
    public Double getSpotCheckScore() {
        return spotCheckScore;
    }

    public void setSpotCheckScore(Double spotCheckScore) {
        this.spotCheckScore = spotCheckScore;
    }

    @Basic
    @Column(name = "is_checked")
    public Byte getIsChecked() {
        return isChecked;
    }

    public void setIsChecked(Byte isChecked) {
        this.isChecked = isChecked;
    }

    @Basic
    @Column(name = "is_spot_checked")
    public Byte getIsSpotChecked() {
        return isSpotChecked;
    }

    public void setIsSpotChecked(Byte isSpotChecked) {
        this.isSpotChecked = isSpotChecked;
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
    @Column(name = "create_time")
    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    @Basic
    @Column(name = "update_time")
    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    @Basic
    @Column(name = "is_deleted")
    public Byte getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Byte isDeleted) {
        this.isDeleted = isDeleted;
    }

    @Basic
    @Column(name = "customer_mobile")
    public String getCustomerMobile() {
        return customerMobile;
    }

    public void setCustomerMobile(String customerMobile) {
        this.customerMobile = customerMobile;
    }

    @Basic
    @Column(name = "is_invalid")
    public Byte getIsInvalid() {
        return isInvalid;
    }

    public void setIsInvalid(Byte isInvalid) {
        this.isInvalid = isInvalid;
    }

    @Basic
    @Column(name = "recheck_user_id")
    public Integer getRecheckUserId() {
        return recheckUserId;
    }

    public void setRecheckUserId(Integer recheckUserId) {
        this.recheckUserId = recheckUserId;
    }

    @Basic
    @Column(name = "apply_code")
    public String getApplyCode() {
        return applyCode;
    }

    public void setApplyCode(String applyCode) {
        this.applyCode = applyCode;
    }

    @Basic
    @Column(name = "distribute_user_id")
    public Integer getDistributeUserId() {
        return distributeUserId;
    }

    public void setDistributeUserId(Integer distributeUserId) {
        this.distributeUserId = distributeUserId;
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
        result = 31 * result + (customerServiceId != null ? customerServiceId.hashCode() : 0);
        result = 31 * result + (extractCheckId != null ? extractCheckId.hashCode() : 0);
        result = 31 * result + (dialogueId != null ? dialogueId.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (audioUrl != null ? audioUrl.hashCode() : 0);
        result = 31 * result + (duration != null ? duration.hashCode() : 0);
        result = 31 * result + (dialogueNumber != null ? dialogueNumber.hashCode() : 0);
        result = 31 * result + (resourceType != null ? resourceType.hashCode() : 0);
        result = 31 * result + (isTranslated != null ? isTranslated.hashCode() : 0);
        result = 31 * result + (isLooked != null ? isLooked.hashCode() : 0);
        result = 31 * result + (serviceCheckRate != null ? serviceCheckRate.hashCode() : 0);
        result = 31 * result + (userCheckRate != null ? userCheckRate.hashCode() : 0);
        result = 31 * result + (violationCount != null ? violationCount.hashCode() : 0);
        result = 31 * result + (recheckScore != null ? recheckScore.hashCode() : 0);
        result = 31 * result + (checkScore != null ? checkScore.hashCode() : 0);
        result = 31 * result + (spotCheckScore != null ? spotCheckScore.hashCode() : 0);
        result = 31 * result + (isChecked != null ? isChecked.hashCode() : 0);
        result = 31 * result + (isSpotChecked != null ? isSpotChecked.hashCode() : 0);
        result = 31 * result + (checkTime != null ? checkTime.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (updateTime != null ? updateTime.hashCode() : 0);
        result = 31 * result + (isDeleted != null ? isDeleted.hashCode() : 0);
        result = 31 * result + (customerMobile != null ? customerMobile.hashCode() : 0);
        result = 31 * result + (isInvalid != null ? isInvalid.hashCode() : 0);
        result = 31 * result + (recheckUserId != null ? recheckUserId.hashCode() : 0);
        result = 31 * result + (applyCode != null ? applyCode.hashCode() : 0);
        result = 31 * result + (distributeUserId != null ? distributeUserId.hashCode() : 0);
        result = 31 * result + (companyId != null ? companyId.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExtractCheckAudioEntity that = (ExtractCheckAudioEntity) o;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (customerServiceId != null ? !customerServiceId.equals(that.customerServiceId) : that.customerServiceId != null)
            return false;
        if (extractCheckId != null ? !extractCheckId.equals(that.extractCheckId) : that.extractCheckId != null)
            return false;
        if (dialogueId != null ? !dialogueId.equals(that.dialogueId) : that.dialogueId != null) return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;
        if (audioUrl != null ? !audioUrl.equals(that.audioUrl) : that.audioUrl != null) return false;
        if (duration != null ? !duration.equals(that.duration) : that.duration != null) return false;
        if (dialogueNumber != null ? !dialogueNumber.equals(that.dialogueNumber) : that.dialogueNumber != null)
            return false;
        if (resourceType != null ? !resourceType.equals(that.resourceType) : that.resourceType != null) return false;
        if (isTranslated != null ? !isTranslated.equals(that.isTranslated) : that.isTranslated != null) return false;
        if (isLooked != null ? !isLooked.equals(that.isLooked) : that.isLooked != null) return false;
        if (serviceCheckRate != null ? !serviceCheckRate.equals(that.serviceCheckRate) : that.serviceCheckRate != null)
            return false;
        if (userCheckRate != null ? !userCheckRate.equals(that.userCheckRate) : that.userCheckRate != null)
            return false;
        if (violationCount != null ? !violationCount.equals(that.violationCount) : that.violationCount != null)
            return false;
        if (recheckScore != null ? !recheckScore.equals(that.recheckScore) : that.recheckScore != null) return false;
        if (checkScore != null ? !checkScore.equals(that.checkScore) : that.checkScore != null) return false;
        if (spotCheckScore != null ? !spotCheckScore.equals(that.spotCheckScore) : that.spotCheckScore != null)
            return false;
        if (isChecked != null ? !isChecked.equals(that.isChecked) : that.isChecked != null) return false;
        if (isSpotChecked != null ? !isSpotChecked.equals(that.isSpotChecked) : that.isSpotChecked != null)
            return false;
        if (checkTime != null ? !checkTime.equals(that.checkTime) : that.checkTime != null) return false;
        if (createTime != null ? !createTime.equals(that.createTime) : that.createTime != null) return false;
        if (updateTime != null ? !updateTime.equals(that.updateTime) : that.updateTime != null) return false;
        if (isDeleted != null ? !isDeleted.equals(that.isDeleted) : that.isDeleted != null) return false;
        if (customerMobile != null ? !customerMobile.equals(that.customerMobile) : that.customerMobile != null)
            return false;
        if (isInvalid != null ? !isInvalid.equals(that.isInvalid) : that.isInvalid != null) return false;
        if (recheckUserId != null ? !recheckUserId.equals(that.recheckUserId) : that.recheckUserId != null)
            return false;
        if (applyCode != null ? !applyCode.equals(that.applyCode) : that.applyCode != null) return false;
        if (distributeUserId != null ? !distributeUserId.equals(that.distributeUserId) : that.distributeUserId != null)
            return false;
        if (companyId != null ? !companyId.equals(that.companyId) : that.companyId != null) return false;
        return true;
    }
}
