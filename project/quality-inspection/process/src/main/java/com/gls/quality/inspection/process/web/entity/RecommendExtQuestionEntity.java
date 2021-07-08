package com.gls.quality.inspection.process.web.entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "recommend_ext_question", schema = "ai_quality_check", catalog = "")
public class RecommendExtQuestionEntity {
    private Integer id;
    private Long companyId;
    private String industryCategory;
    private String industryCategoryIds;
    private Integer semanticLabelId;
    private String phrasing;
    private String wordSet;
    private Timestamp createTime;

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
    @Column(name = "industry_category")
    public String getIndustryCategory() {
        return industryCategory;
    }

    public void setIndustryCategory(String industryCategory) {
        this.industryCategory = industryCategory;
    }

    @Basic
    @Column(name = "industry_category_ids")
    public String getIndustryCategoryIds() {
        return industryCategoryIds;
    }

    public void setIndustryCategoryIds(String industryCategoryIds) {
        this.industryCategoryIds = industryCategoryIds;
    }

    @Basic
    @Column(name = "semantic_label_id")
    public Integer getSemanticLabelId() {
        return semanticLabelId;
    }

    public void setSemanticLabelId(Integer semanticLabelId) {
        this.semanticLabelId = semanticLabelId;
    }

    @Basic
    @Column(name = "phrasing")
    public String getPhrasing() {
        return phrasing;
    }

    public void setPhrasing(String phrasing) {
        this.phrasing = phrasing;
    }

    @Basic
    @Column(name = "word_set")
    public String getWordSet() {
        return wordSet;
    }

    public void setWordSet(String wordSet) {
        this.wordSet = wordSet;
    }

    @Basic
    @Column(name = "create_time")
    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (companyId != null ? companyId.hashCode() : 0);
        result = 31 * result + (industryCategory != null ? industryCategory.hashCode() : 0);
        result = 31 * result + (industryCategoryIds != null ? industryCategoryIds.hashCode() : 0);
        result = 31 * result + (semanticLabelId != null ? semanticLabelId.hashCode() : 0);
        result = 31 * result + (phrasing != null ? phrasing.hashCode() : 0);
        result = 31 * result + (wordSet != null ? wordSet.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RecommendExtQuestionEntity that = (RecommendExtQuestionEntity) o;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (companyId != null ? !companyId.equals(that.companyId) : that.companyId != null) return false;
        if (industryCategory != null ? !industryCategory.equals(that.industryCategory) : that.industryCategory != null)
            return false;
        if (industryCategoryIds != null ? !industryCategoryIds.equals(that.industryCategoryIds) : that.industryCategoryIds != null)
            return false;
        if (semanticLabelId != null ? !semanticLabelId.equals(that.semanticLabelId) : that.semanticLabelId != null)
            return false;
        if (phrasing != null ? !phrasing.equals(that.phrasing) : that.phrasing != null) return false;
        if (wordSet != null ? !wordSet.equals(that.wordSet) : that.wordSet != null) return false;
        if (createTime != null ? !createTime.equals(that.createTime) : that.createTime != null) return false;
        return true;
    }
}
