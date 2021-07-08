package com.gls.quality.inspection.process.web.entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "tb_score_template", schema = "ai_quality_check", catalog = "")
public class ScoreTemplateEntity {
    private Integer id;
    private Integer templateId;
    private String modelName;
    private Integer modelId;
    private Integer userId;
    private String templateName;
    private Integer baseScore;
    private Integer scoreItemsNumber;
    private Byte isDeleted;
    private Timestamp createTime;
    private Timestamp updateTime;
    private Byte status;
    private Long companyId;
    private Byte scoreBaseline;

    @Id
    @Column(name = "id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "template_id")
    public Integer getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Integer templateId) {
        this.templateId = templateId;
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
    @Column(name = "model_id")
    public Integer getModelId() {
        return modelId;
    }

    public void setModelId(Integer modelId) {
        this.modelId = modelId;
    }

    @Basic
    @Column(name = "user_id")
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "template_name")
    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
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
    @Column(name = "score_items_number")
    public Integer getScoreItemsNumber() {
        return scoreItemsNumber;
    }

    public void setScoreItemsNumber(Integer scoreItemsNumber) {
        this.scoreItemsNumber = scoreItemsNumber;
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
    @Column(name = "status")
    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    @Basic
    @Column(name = "company_id")
    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    @Basic
    @Column(name = "score_baseline")
    public Byte getScoreBaseline() {
        return scoreBaseline;
    }

    public void setScoreBaseline(Byte scoreBaseline) {
        this.scoreBaseline = scoreBaseline;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (templateId != null ? templateId.hashCode() : 0);
        result = 31 * result + (modelName != null ? modelName.hashCode() : 0);
        result = 31 * result + (modelId != null ? modelId.hashCode() : 0);
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + (templateName != null ? templateName.hashCode() : 0);
        result = 31 * result + (baseScore != null ? baseScore.hashCode() : 0);
        result = 31 * result + (scoreItemsNumber != null ? scoreItemsNumber.hashCode() : 0);
        result = 31 * result + (isDeleted != null ? isDeleted.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (updateTime != null ? updateTime.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (companyId != null ? companyId.hashCode() : 0);
        result = 31 * result + (scoreBaseline != null ? scoreBaseline.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ScoreTemplateEntity that = (ScoreTemplateEntity) o;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (templateId != null ? !templateId.equals(that.templateId) : that.templateId != null) return false;
        if (modelName != null ? !modelName.equals(that.modelName) : that.modelName != null) return false;
        if (modelId != null ? !modelId.equals(that.modelId) : that.modelId != null) return false;
        if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;
        if (templateName != null ? !templateName.equals(that.templateName) : that.templateName != null) return false;
        if (baseScore != null ? !baseScore.equals(that.baseScore) : that.baseScore != null) return false;
        if (scoreItemsNumber != null ? !scoreItemsNumber.equals(that.scoreItemsNumber) : that.scoreItemsNumber != null)
            return false;
        if (isDeleted != null ? !isDeleted.equals(that.isDeleted) : that.isDeleted != null) return false;
        if (createTime != null ? !createTime.equals(that.createTime) : that.createTime != null) return false;
        if (updateTime != null ? !updateTime.equals(that.updateTime) : that.updateTime != null) return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;
        if (companyId != null ? !companyId.equals(that.companyId) : that.companyId != null) return false;
        if (scoreBaseline != null ? !scoreBaseline.equals(that.scoreBaseline) : that.scoreBaseline != null)
            return false;
        return true;
    }
}
