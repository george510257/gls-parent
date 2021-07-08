package com.gls.quality.inspection.process.web.entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "label", schema = "ai_quality_check", catalog = "")
public class LabelEntity {
    private Integer id;
    private Long companyId;
    private Integer modelId;
    private String semanticCategory;
    private String semanticLabel;
    private Integer semanticRole;
    private Integer status;
    private String extQuestion;
    private String recommendExtQuestion;
    private String rule;
    private Timestamp createTime;
    private Timestamp updateTime;
    private Timestamp deleteTime;

    @Id
    @Column(name = "id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
    @Column(name = "model_id")
    public Integer getModelId() {
        return modelId;
    }

    public void setModelId(Integer modelId) {
        this.modelId = modelId;
    }

    @Basic
    @Column(name = "semantic_category")
    public String getSemanticCategory() {
        return semanticCategory;
    }

    public void setSemanticCategory(String semanticCategory) {
        this.semanticCategory = semanticCategory;
    }

    @Basic
    @Column(name = "semantic_label")
    public String getSemanticLabel() {
        return semanticLabel;
    }

    public void setSemanticLabel(String semanticLabel) {
        this.semanticLabel = semanticLabel;
    }

    @Basic
    @Column(name = "semantic_role")
    public Integer getSemanticRole() {
        return semanticRole;
    }

    public void setSemanticRole(Integer semanticRole) {
        this.semanticRole = semanticRole;
    }

    @Basic
    @Column(name = "status")
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Basic
    @Column(name = "ext_question")
    public String getExtQuestion() {
        return extQuestion;
    }

    public void setExtQuestion(String extQuestion) {
        this.extQuestion = extQuestion;
    }

    @Basic
    @Column(name = "recommend_ext_question")
    public String getRecommendExtQuestion() {
        return recommendExtQuestion;
    }

    public void setRecommendExtQuestion(String recommendExtQuestion) {
        this.recommendExtQuestion = recommendExtQuestion;
    }

    @Basic
    @Column(name = "rule")
    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
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
    @Column(name = "delete_time")
    public Timestamp getDeleteTime() {
        return deleteTime;
    }

    public void setDeleteTime(Timestamp deleteTime) {
        this.deleteTime = deleteTime;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (companyId != null ? companyId.hashCode() : 0);
        result = 31 * result + (modelId != null ? modelId.hashCode() : 0);
        result = 31 * result + (semanticCategory != null ? semanticCategory.hashCode() : 0);
        result = 31 * result + (semanticLabel != null ? semanticLabel.hashCode() : 0);
        result = 31 * result + (semanticRole != null ? semanticRole.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (extQuestion != null ? extQuestion.hashCode() : 0);
        result = 31 * result + (recommendExtQuestion != null ? recommendExtQuestion.hashCode() : 0);
        result = 31 * result + (rule != null ? rule.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (updateTime != null ? updateTime.hashCode() : 0);
        result = 31 * result + (deleteTime != null ? deleteTime.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LabelEntity that = (LabelEntity) o;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (companyId != null ? !companyId.equals(that.companyId) : that.companyId != null) return false;
        if (modelId != null ? !modelId.equals(that.modelId) : that.modelId != null) return false;
        if (semanticCategory != null ? !semanticCategory.equals(that.semanticCategory) : that.semanticCategory != null)
            return false;
        if (semanticLabel != null ? !semanticLabel.equals(that.semanticLabel) : that.semanticLabel != null)
            return false;
        if (semanticRole != null ? !semanticRole.equals(that.semanticRole) : that.semanticRole != null) return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;
        if (extQuestion != null ? !extQuestion.equals(that.extQuestion) : that.extQuestion != null) return false;
        if (recommendExtQuestion != null ? !recommendExtQuestion.equals(that.recommendExtQuestion) : that.recommendExtQuestion != null)
            return false;
        if (rule != null ? !rule.equals(that.rule) : that.rule != null) return false;
        if (createTime != null ? !createTime.equals(that.createTime) : that.createTime != null) return false;
        if (updateTime != null ? !updateTime.equals(that.updateTime) : that.updateTime != null) return false;
        if (deleteTime != null ? !deleteTime.equals(that.deleteTime) : that.deleteTime != null) return false;
        return true;
    }
}
