package com.gls.quality.inspection.process.web.entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "tb_extract_check", schema = "ai_quality_check", catalog = "")
public class ExtractCheckEntity {
    private Integer id;
    private String userId;
    private String extractCheckName;
    private Byte differentiateRole;
    private Integer scoreTemplateId;
    private Integer industryCategoryId;
    private Integer totalDuration;
    private String modelName;
    private Timestamp deadline;
    private Byte status;
    private Integer extractCheckSchedule;
    private Integer modelId;
    private String fileUrl;
    private Byte resourceType;
    private Byte isChecked;
    private Byte isFinished;
    private Timestamp createTime;
    private Timestamp updateTime;
    private Byte isDeleted;
    private Integer extractCheckType;
    private Timestamp startTime;
    private Integer baseScore;
    private Timestamp endTime;
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
    @Column(name = "user_id")
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "extract_check_name")
    public String getExtractCheckName() {
        return extractCheckName;
    }

    public void setExtractCheckName(String extractCheckName) {
        this.extractCheckName = extractCheckName;
    }

    @Basic
    @Column(name = "differentiate_role")
    public Byte getDifferentiateRole() {
        return differentiateRole;
    }

    public void setDifferentiateRole(Byte differentiateRole) {
        this.differentiateRole = differentiateRole;
    }

    @Basic
    @Column(name = "score_template_id")
    public Integer getScoreTemplateId() {
        return scoreTemplateId;
    }

    public void setScoreTemplateId(Integer scoreTemplateId) {
        this.scoreTemplateId = scoreTemplateId;
    }

    @Basic
    @Column(name = "industry_category_id")
    public Integer getIndustryCategoryId() {
        return industryCategoryId;
    }

    public void setIndustryCategoryId(Integer industryCategoryId) {
        this.industryCategoryId = industryCategoryId;
    }

    @Basic
    @Column(name = "total_duration")
    public Integer getTotalDuration() {
        return totalDuration;
    }

    public void setTotalDuration(Integer totalDuration) {
        this.totalDuration = totalDuration;
    }

    @Basic
    @Column(name = "model_name")
    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
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
    @Column(name = "status")
    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    @Basic
    @Column(name = "extract_check_schedule")
    public Integer getExtractCheckSchedule() {
        return extractCheckSchedule;
    }

    public void setExtractCheckSchedule(Integer extractCheckSchedule) {
        this.extractCheckSchedule = extractCheckSchedule;
    }

    @Basic
    @Column(name = "model_id")
    public Integer getModelId() {
        return modelId;
    }

    public void setModelId(Integer modelId) {
        this.modelId = modelId;
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
    @Column(name = "resource_type")
    public Byte getResourceType() {
        return resourceType;
    }

    public void setResourceType(Byte resourceType) {
        this.resourceType = resourceType;
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
    @Column(name = "is_finished")
    public Byte getIsFinished() {
        return isFinished;
    }

    public void setIsFinished(Byte isFinished) {
        this.isFinished = isFinished;
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
    @Column(name = "extract_check_type")
    public Integer getExtractCheckType() {
        return extractCheckType;
    }

    public void setExtractCheckType(Integer extractCheckType) {
        this.extractCheckType = extractCheckType;
    }

    @Basic
    @Column(name = "start_time")
    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    @Basic
    @Column(name = "base_score")
    public Integer getBaseScore() {
        return baseScore;
    }

    public void setBaseScore(Integer baseScore) {
        this.baseScore = baseScore;
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
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + (extractCheckName != null ? extractCheckName.hashCode() : 0);
        result = 31 * result + (differentiateRole != null ? differentiateRole.hashCode() : 0);
        result = 31 * result + (scoreTemplateId != null ? scoreTemplateId.hashCode() : 0);
        result = 31 * result + (industryCategoryId != null ? industryCategoryId.hashCode() : 0);
        result = 31 * result + (totalDuration != null ? totalDuration.hashCode() : 0);
        result = 31 * result + (modelName != null ? modelName.hashCode() : 0);
        result = 31 * result + (deadline != null ? deadline.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (extractCheckSchedule != null ? extractCheckSchedule.hashCode() : 0);
        result = 31 * result + (modelId != null ? modelId.hashCode() : 0);
        result = 31 * result + (fileUrl != null ? fileUrl.hashCode() : 0);
        result = 31 * result + (resourceType != null ? resourceType.hashCode() : 0);
        result = 31 * result + (isChecked != null ? isChecked.hashCode() : 0);
        result = 31 * result + (isFinished != null ? isFinished.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (updateTime != null ? updateTime.hashCode() : 0);
        result = 31 * result + (isDeleted != null ? isDeleted.hashCode() : 0);
        result = 31 * result + (extractCheckType != null ? extractCheckType.hashCode() : 0);
        result = 31 * result + (startTime != null ? startTime.hashCode() : 0);
        result = 31 * result + (baseScore != null ? baseScore.hashCode() : 0);
        result = 31 * result + (endTime != null ? endTime.hashCode() : 0);
        result = 31 * result + (companyId != null ? companyId.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExtractCheckEntity that = (ExtractCheckEntity) o;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;
        if (extractCheckName != null ? !extractCheckName.equals(that.extractCheckName) : that.extractCheckName != null)
            return false;
        if (differentiateRole != null ? !differentiateRole.equals(that.differentiateRole) : that.differentiateRole != null)
            return false;
        if (scoreTemplateId != null ? !scoreTemplateId.equals(that.scoreTemplateId) : that.scoreTemplateId != null)
            return false;
        if (industryCategoryId != null ? !industryCategoryId.equals(that.industryCategoryId) : that.industryCategoryId != null)
            return false;
        if (totalDuration != null ? !totalDuration.equals(that.totalDuration) : that.totalDuration != null)
            return false;
        if (modelName != null ? !modelName.equals(that.modelName) : that.modelName != null) return false;
        if (deadline != null ? !deadline.equals(that.deadline) : that.deadline != null) return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;
        if (extractCheckSchedule != null ? !extractCheckSchedule.equals(that.extractCheckSchedule) : that.extractCheckSchedule != null)
            return false;
        if (modelId != null ? !modelId.equals(that.modelId) : that.modelId != null) return false;
        if (fileUrl != null ? !fileUrl.equals(that.fileUrl) : that.fileUrl != null) return false;
        if (resourceType != null ? !resourceType.equals(that.resourceType) : that.resourceType != null) return false;
        if (isChecked != null ? !isChecked.equals(that.isChecked) : that.isChecked != null) return false;
        if (isFinished != null ? !isFinished.equals(that.isFinished) : that.isFinished != null) return false;
        if (createTime != null ? !createTime.equals(that.createTime) : that.createTime != null) return false;
        if (updateTime != null ? !updateTime.equals(that.updateTime) : that.updateTime != null) return false;
        if (isDeleted != null ? !isDeleted.equals(that.isDeleted) : that.isDeleted != null) return false;
        if (extractCheckType != null ? !extractCheckType.equals(that.extractCheckType) : that.extractCheckType != null)
            return false;
        if (startTime != null ? !startTime.equals(that.startTime) : that.startTime != null) return false;
        if (baseScore != null ? !baseScore.equals(that.baseScore) : that.baseScore != null) return false;
        if (endTime != null ? !endTime.equals(that.endTime) : that.endTime != null) return false;
        if (companyId != null ? !companyId.equals(that.companyId) : that.companyId != null) return false;
        return true;
    }
}
