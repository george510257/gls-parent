package com.gls.quality.inspection.process.web.entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "tb_spot_check", schema = "ai_quality_check", catalog = "")
public class SpotCheckEntity {
    private Integer id;
    private String spotCheckName;
    private Integer extractCheckId;
    private Timestamp deadline;
    private Integer spotCheckSchedule;
    private Integer dialogueNumber;
    private Integer createdUserId;
    private Byte isInvalided;
    private Byte status;
    private Byte isDeleted;
    private Timestamp createTime;
    private Timestamp updateTime;
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
    @Column(name = "spot_check_name")
    public String getSpotCheckName() {
        return spotCheckName;
    }

    public void setSpotCheckName(String spotCheckName) {
        this.spotCheckName = spotCheckName;
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
    @Column(name = "deadline")
    public Timestamp getDeadline() {
        return deadline;
    }

    public void setDeadline(Timestamp deadline) {
        this.deadline = deadline;
    }

    @Basic
    @Column(name = "spot_check_schedule")
    public Integer getSpotCheckSchedule() {
        return spotCheckSchedule;
    }

    public void setSpotCheckSchedule(Integer spotCheckSchedule) {
        this.spotCheckSchedule = spotCheckSchedule;
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
    @Column(name = "created_user_id")
    public Integer getCreatedUserId() {
        return createdUserId;
    }

    public void setCreatedUserId(Integer createdUserId) {
        this.createdUserId = createdUserId;
    }

    @Basic
    @Column(name = "is_invalided")
    public Byte getIsInvalided() {
        return isInvalided;
    }

    public void setIsInvalided(Byte isInvalided) {
        this.isInvalided = isInvalided;
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
    @Column(name = "is_deleted")
    public Byte getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Byte isDeleted) {
        this.isDeleted = isDeleted;
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
        result = 31 * result + (spotCheckName != null ? spotCheckName.hashCode() : 0);
        result = 31 * result + (extractCheckId != null ? extractCheckId.hashCode() : 0);
        result = 31 * result + (deadline != null ? deadline.hashCode() : 0);
        result = 31 * result + (spotCheckSchedule != null ? spotCheckSchedule.hashCode() : 0);
        result = 31 * result + (dialogueNumber != null ? dialogueNumber.hashCode() : 0);
        result = 31 * result + (createdUserId != null ? createdUserId.hashCode() : 0);
        result = 31 * result + (isInvalided != null ? isInvalided.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (isDeleted != null ? isDeleted.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (updateTime != null ? updateTime.hashCode() : 0);
        result = 31 * result + (companyId != null ? companyId.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SpotCheckEntity that = (SpotCheckEntity) o;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (spotCheckName != null ? !spotCheckName.equals(that.spotCheckName) : that.spotCheckName != null)
            return false;
        if (extractCheckId != null ? !extractCheckId.equals(that.extractCheckId) : that.extractCheckId != null)
            return false;
        if (deadline != null ? !deadline.equals(that.deadline) : that.deadline != null) return false;
        if (spotCheckSchedule != null ? !spotCheckSchedule.equals(that.spotCheckSchedule) : that.spotCheckSchedule != null)
            return false;
        if (dialogueNumber != null ? !dialogueNumber.equals(that.dialogueNumber) : that.dialogueNumber != null)
            return false;
        if (createdUserId != null ? !createdUserId.equals(that.createdUserId) : that.createdUserId != null)
            return false;
        if (isInvalided != null ? !isInvalided.equals(that.isInvalided) : that.isInvalided != null) return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;
        if (isDeleted != null ? !isDeleted.equals(that.isDeleted) : that.isDeleted != null) return false;
        if (createTime != null ? !createTime.equals(that.createTime) : that.createTime != null) return false;
        if (updateTime != null ? !updateTime.equals(that.updateTime) : that.updateTime != null) return false;
        if (companyId != null ? !companyId.equals(that.companyId) : that.companyId != null) return false;
        return true;
    }
}
