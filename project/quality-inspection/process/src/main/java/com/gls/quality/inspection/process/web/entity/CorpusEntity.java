package com.gls.quality.inspection.process.web.entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "corpus", schema = "ai_quality_check", catalog = "")
public class CorpusEntity {
    private Integer id;
    private Long companyId;
    private Integer modelId;
    private String content;
    private String audioUrl;
    private Byte translateStatus;
    private Integer status;
    private Integer isAnnotated;
    private Integer audioLen;
    private Integer totalLabelId;
    private Integer complexLabelId;
    private String semanticLabelList;
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
    @Column(name = "model_id")
    public Integer getModelId() {
        return modelId;
    }

    public void setModelId(Integer modelId) {
        this.modelId = modelId;
    }

    @Basic
    @Column(name = "content")
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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
    @Column(name = "translate_status")
    public Byte getTranslateStatus() {
        return translateStatus;
    }

    public void setTranslateStatus(Byte translateStatus) {
        this.translateStatus = translateStatus;
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
    @Column(name = "is_annotated")
    public Integer getIsAnnotated() {
        return isAnnotated;
    }

    public void setIsAnnotated(Integer isAnnotated) {
        this.isAnnotated = isAnnotated;
    }

    @Basic
    @Column(name = "audio_len")
    public Integer getAudioLen() {
        return audioLen;
    }

    public void setAudioLen(Integer audioLen) {
        this.audioLen = audioLen;
    }

    @Basic
    @Column(name = "total_label_id")
    public Integer getTotalLabelId() {
        return totalLabelId;
    }

    public void setTotalLabelId(Integer totalLabelId) {
        this.totalLabelId = totalLabelId;
    }

    @Basic
    @Column(name = "complex_label_id")
    public Integer getComplexLabelId() {
        return complexLabelId;
    }

    public void setComplexLabelId(Integer complexLabelId) {
        this.complexLabelId = complexLabelId;
    }

    @Basic
    @Column(name = "semantic_label_list")
    public String getSemanticLabelList() {
        return semanticLabelList;
    }

    public void setSemanticLabelList(String semanticLabelList) {
        this.semanticLabelList = semanticLabelList;
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
        result = 31 * result + (modelId != null ? modelId.hashCode() : 0);
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (audioUrl != null ? audioUrl.hashCode() : 0);
        result = 31 * result + (translateStatus != null ? translateStatus.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (isAnnotated != null ? isAnnotated.hashCode() : 0);
        result = 31 * result + (audioLen != null ? audioLen.hashCode() : 0);
        result = 31 * result + (totalLabelId != null ? totalLabelId.hashCode() : 0);
        result = 31 * result + (complexLabelId != null ? complexLabelId.hashCode() : 0);
        result = 31 * result + (semanticLabelList != null ? semanticLabelList.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CorpusEntity that = (CorpusEntity) o;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (companyId != null ? !companyId.equals(that.companyId) : that.companyId != null) return false;
        if (modelId != null ? !modelId.equals(that.modelId) : that.modelId != null) return false;
        if (content != null ? !content.equals(that.content) : that.content != null) return false;
        if (audioUrl != null ? !audioUrl.equals(that.audioUrl) : that.audioUrl != null) return false;
        if (translateStatus != null ? !translateStatus.equals(that.translateStatus) : that.translateStatus != null)
            return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;
        if (isAnnotated != null ? !isAnnotated.equals(that.isAnnotated) : that.isAnnotated != null) return false;
        if (audioLen != null ? !audioLen.equals(that.audioLen) : that.audioLen != null) return false;
        if (totalLabelId != null ? !totalLabelId.equals(that.totalLabelId) : that.totalLabelId != null) return false;
        if (complexLabelId != null ? !complexLabelId.equals(that.complexLabelId) : that.complexLabelId != null)
            return false;
        if (semanticLabelList != null ? !semanticLabelList.equals(that.semanticLabelList) : that.semanticLabelList != null)
            return false;
        if (createTime != null ? !createTime.equals(that.createTime) : that.createTime != null) return false;
        return true;
    }
}
