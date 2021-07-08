package com.gls.quality.inspection.process.web.entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "tb_model_result_text", schema = "ai_quality_check", catalog = "")
public class ModelResultTextEntity {
    private Integer id;
    private Integer modelResultId;
    private Integer extractCheckAudioId;
    private Integer extractCheckAudioTextId;
    private String info;
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
    @Column(name = "model_result_id")
    public Integer getModelResultId() {
        return modelResultId;
    }

    public void setModelResultId(Integer modelResultId) {
        this.modelResultId = modelResultId;
    }

    @Basic
    @Column(name = "extract_check_audio_id")
    public Integer getExtractCheckAudioId() {
        return extractCheckAudioId;
    }

    public void setExtractCheckAudioId(Integer extractCheckAudioId) {
        this.extractCheckAudioId = extractCheckAudioId;
    }

    @Basic
    @Column(name = "extract_check_audio_text_id")
    public Integer getExtractCheckAudioTextId() {
        return extractCheckAudioTextId;
    }

    public void setExtractCheckAudioTextId(Integer extractCheckAudioTextId) {
        this.extractCheckAudioTextId = extractCheckAudioTextId;
    }

    @Basic
    @Column(name = "info")
    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
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
        result = 31 * result + (modelResultId != null ? modelResultId.hashCode() : 0);
        result = 31 * result + (extractCheckAudioId != null ? extractCheckAudioId.hashCode() : 0);
        result = 31 * result + (extractCheckAudioTextId != null ? extractCheckAudioTextId.hashCode() : 0);
        result = 31 * result + (info != null ? info.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (updateTime != null ? updateTime.hashCode() : 0);
        result = 31 * result + (companyId != null ? companyId.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ModelResultTextEntity that = (ModelResultTextEntity) o;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (modelResultId != null ? !modelResultId.equals(that.modelResultId) : that.modelResultId != null)
            return false;
        if (extractCheckAudioId != null ? !extractCheckAudioId.equals(that.extractCheckAudioId) : that.extractCheckAudioId != null)
            return false;
        if (extractCheckAudioTextId != null ? !extractCheckAudioTextId.equals(that.extractCheckAudioTextId) : that.extractCheckAudioTextId != null)
            return false;
        if (info != null ? !info.equals(that.info) : that.info != null) return false;
        if (createTime != null ? !createTime.equals(that.createTime) : that.createTime != null) return false;
        if (updateTime != null ? !updateTime.equals(that.updateTime) : that.updateTime != null) return false;
        if (companyId != null ? !companyId.equals(that.companyId) : that.companyId != null) return false;
        return true;
    }
}
