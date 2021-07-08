package com.gls.quality.inspection.process.web.entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "label_test_data", schema = "ai_quality_check", catalog = "")
public class LabelTestDataEntity {
    private Integer id;
    private Integer modelId;
    private String name;
    private Integer status;
    private Double totalLabelPassingRate;
    private Double voiceCheckPassingRate;
    private Double semanticLabelPassingRate;
    private Double complexLabelPassingRate;
    private Timestamp createTime;
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
    @Column(name = "model_id")
    public Integer getModelId() {
        return modelId;
    }

    public void setModelId(Integer modelId) {
        this.modelId = modelId;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
    @Column(name = "total_label_passing_rate")
    public Double getTotalLabelPassingRate() {
        return totalLabelPassingRate;
    }

    public void setTotalLabelPassingRate(Double totalLabelPassingRate) {
        this.totalLabelPassingRate = totalLabelPassingRate;
    }

    @Basic
    @Column(name = "voice_check_passing_rate")
    public Double getVoiceCheckPassingRate() {
        return voiceCheckPassingRate;
    }

    public void setVoiceCheckPassingRate(Double voiceCheckPassingRate) {
        this.voiceCheckPassingRate = voiceCheckPassingRate;
    }

    @Basic
    @Column(name = "semantic_label_passing_rate")
    public Double getSemanticLabelPassingRate() {
        return semanticLabelPassingRate;
    }

    public void setSemanticLabelPassingRate(Double semanticLabelPassingRate) {
        this.semanticLabelPassingRate = semanticLabelPassingRate;
    }

    @Basic
    @Column(name = "complex_label_passing_rate")
    public Double getComplexLabelPassingRate() {
        return complexLabelPassingRate;
    }

    public void setComplexLabelPassingRate(Double complexLabelPassingRate) {
        this.complexLabelPassingRate = complexLabelPassingRate;
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
        result = 31 * result + (modelId != null ? modelId.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (totalLabelPassingRate != null ? totalLabelPassingRate.hashCode() : 0);
        result = 31 * result + (voiceCheckPassingRate != null ? voiceCheckPassingRate.hashCode() : 0);
        result = 31 * result + (semanticLabelPassingRate != null ? semanticLabelPassingRate.hashCode() : 0);
        result = 31 * result + (complexLabelPassingRate != null ? complexLabelPassingRate.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (companyId != null ? companyId.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LabelTestDataEntity that = (LabelTestDataEntity) o;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (modelId != null ? !modelId.equals(that.modelId) : that.modelId != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;
        if (totalLabelPassingRate != null ? !totalLabelPassingRate.equals(that.totalLabelPassingRate) : that.totalLabelPassingRate != null)
            return false;
        if (voiceCheckPassingRate != null ? !voiceCheckPassingRate.equals(that.voiceCheckPassingRate) : that.voiceCheckPassingRate != null)
            return false;
        if (semanticLabelPassingRate != null ? !semanticLabelPassingRate.equals(that.semanticLabelPassingRate) : that.semanticLabelPassingRate != null)
            return false;
        if (complexLabelPassingRate != null ? !complexLabelPassingRate.equals(that.complexLabelPassingRate) : that.complexLabelPassingRate != null)
            return false;
        if (createTime != null ? !createTime.equals(that.createTime) : that.createTime != null) return false;
        if (companyId != null ? !companyId.equals(that.companyId) : that.companyId != null) return false;
        return true;
    }
}
