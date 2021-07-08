package com.gls.quality.inspection.process.web.entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "tb_score_items", schema = "ai_quality_check", catalog = "")
public class ScoreItemsEntity {
    private Integer id;
    private Integer scoreTemplateId;
    private Byte type;
    private String scoreItemsTitle;
    private Byte scoreStrategy;
    private Byte inspectionObject;
    private Byte scoreAttribute;
    private Double score;
    private Timestamp createTime;
    private Timestamp updateTime;
    private Long companyId;
    private Byte scorePrinciple;

    @Id
    @Column(name = "id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
    @Column(name = "type")
    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    @Basic
    @Column(name = "score_items_title")
    public String getScoreItemsTitle() {
        return scoreItemsTitle;
    }

    public void setScoreItemsTitle(String scoreItemsTitle) {
        this.scoreItemsTitle = scoreItemsTitle;
    }

    @Basic
    @Column(name = "score_strategy")
    public Byte getScoreStrategy() {
        return scoreStrategy;
    }

    public void setScoreStrategy(Byte scoreStrategy) {
        this.scoreStrategy = scoreStrategy;
    }

    @Basic
    @Column(name = "inspection_object")
    public Byte getInspectionObject() {
        return inspectionObject;
    }

    public void setInspectionObject(Byte inspectionObject) {
        this.inspectionObject = inspectionObject;
    }

    @Basic
    @Column(name = "score_attribute")
    public Byte getScoreAttribute() {
        return scoreAttribute;
    }

    public void setScoreAttribute(Byte scoreAttribute) {
        this.scoreAttribute = scoreAttribute;
    }

    @Basic
    @Column(name = "score")
    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
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

    @Basic
    @Column(name = "score_principle")
    public Byte getScorePrinciple() {
        return scorePrinciple;
    }

    public void setScorePrinciple(Byte scorePrinciple) {
        this.scorePrinciple = scorePrinciple;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (scoreTemplateId != null ? scoreTemplateId.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (scoreItemsTitle != null ? scoreItemsTitle.hashCode() : 0);
        result = 31 * result + (scoreStrategy != null ? scoreStrategy.hashCode() : 0);
        result = 31 * result + (inspectionObject != null ? inspectionObject.hashCode() : 0);
        result = 31 * result + (scoreAttribute != null ? scoreAttribute.hashCode() : 0);
        result = 31 * result + (score != null ? score.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (updateTime != null ? updateTime.hashCode() : 0);
        result = 31 * result + (companyId != null ? companyId.hashCode() : 0);
        result = 31 * result + (scorePrinciple != null ? scorePrinciple.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ScoreItemsEntity that = (ScoreItemsEntity) o;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (scoreTemplateId != null ? !scoreTemplateId.equals(that.scoreTemplateId) : that.scoreTemplateId != null)
            return false;
        if (type != null ? !type.equals(that.type) : that.type != null) return false;
        if (scoreItemsTitle != null ? !scoreItemsTitle.equals(that.scoreItemsTitle) : that.scoreItemsTitle != null)
            return false;
        if (scoreStrategy != null ? !scoreStrategy.equals(that.scoreStrategy) : that.scoreStrategy != null)
            return false;
        if (inspectionObject != null ? !inspectionObject.equals(that.inspectionObject) : that.inspectionObject != null)
            return false;
        if (scoreAttribute != null ? !scoreAttribute.equals(that.scoreAttribute) : that.scoreAttribute != null)
            return false;
        if (score != null ? !score.equals(that.score) : that.score != null) return false;
        if (createTime != null ? !createTime.equals(that.createTime) : that.createTime != null) return false;
        if (updateTime != null ? !updateTime.equals(that.updateTime) : that.updateTime != null) return false;
        if (companyId != null ? !companyId.equals(that.companyId) : that.companyId != null) return false;
        if (scorePrinciple != null ? !scorePrinciple.equals(that.scorePrinciple) : that.scorePrinciple != null)
            return false;
        return true;
    }
}
