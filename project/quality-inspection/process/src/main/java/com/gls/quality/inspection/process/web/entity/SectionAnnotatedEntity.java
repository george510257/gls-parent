package com.gls.quality.inspection.process.web.entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "section_annotated", schema = "ai_quality_check", catalog = "")
public class SectionAnnotatedEntity {
    private Integer id;
    private Integer corpusId;
    private String selection;
    private String totalLabel;
    private String singleLabel;
    private String complexLabel;
    private String extqRule;
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
    @Column(name = "corpus_id")
    public Integer getCorpusId() {
        return corpusId;
    }

    public void setCorpusId(Integer corpusId) {
        this.corpusId = corpusId;
    }

    @Basic
    @Column(name = "selection")
    public String getSelection() {
        return selection;
    }

    public void setSelection(String selection) {
        this.selection = selection;
    }

    @Basic
    @Column(name = "total_label")
    public String getTotalLabel() {
        return totalLabel;
    }

    public void setTotalLabel(String totalLabel) {
        this.totalLabel = totalLabel;
    }

    @Basic
    @Column(name = "single_label")
    public String getSingleLabel() {
        return singleLabel;
    }

    public void setSingleLabel(String singleLabel) {
        this.singleLabel = singleLabel;
    }

    @Basic
    @Column(name = "complex_label")
    public String getComplexLabel() {
        return complexLabel;
    }

    public void setComplexLabel(String complexLabel) {
        this.complexLabel = complexLabel;
    }

    @Basic
    @Column(name = "extq_rule")
    public String getExtqRule() {
        return extqRule;
    }

    public void setExtqRule(String extqRule) {
        this.extqRule = extqRule;
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
        result = 31 * result + (corpusId != null ? corpusId.hashCode() : 0);
        result = 31 * result + (selection != null ? selection.hashCode() : 0);
        result = 31 * result + (totalLabel != null ? totalLabel.hashCode() : 0);
        result = 31 * result + (singleLabel != null ? singleLabel.hashCode() : 0);
        result = 31 * result + (complexLabel != null ? complexLabel.hashCode() : 0);
        result = 31 * result + (extqRule != null ? extqRule.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (updateTime != null ? updateTime.hashCode() : 0);
        result = 31 * result + (companyId != null ? companyId.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SectionAnnotatedEntity that = (SectionAnnotatedEntity) o;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (corpusId != null ? !corpusId.equals(that.corpusId) : that.corpusId != null) return false;
        if (selection != null ? !selection.equals(that.selection) : that.selection != null) return false;
        if (totalLabel != null ? !totalLabel.equals(that.totalLabel) : that.totalLabel != null) return false;
        if (singleLabel != null ? !singleLabel.equals(that.singleLabel) : that.singleLabel != null) return false;
        if (complexLabel != null ? !complexLabel.equals(that.complexLabel) : that.complexLabel != null) return false;
        if (extqRule != null ? !extqRule.equals(that.extqRule) : that.extqRule != null) return false;
        if (createTime != null ? !createTime.equals(that.createTime) : that.createTime != null) return false;
        if (updateTime != null ? !updateTime.equals(that.updateTime) : that.updateTime != null) return false;
        if (companyId != null ? !companyId.equals(that.companyId) : that.companyId != null) return false;
        return true;
    }
}
