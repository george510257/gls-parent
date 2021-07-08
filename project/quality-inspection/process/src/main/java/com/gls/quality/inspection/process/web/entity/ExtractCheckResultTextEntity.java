package com.gls.quality.inspection.process.web.entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "tb_extract_check_result_text", schema = "ai_quality_check", catalog = "")
public class ExtractCheckResultTextEntity {
    private Integer id;
    private Integer extractCheckResultId;
    private Integer extractCheckAudioId;
    private Integer extractCheckAudioTextId;
    private String info;
    private Double violationsScore;
    private Byte isError;
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
    @Column(name = "extract_check_result_id")
    public Integer getExtractCheckResultId() {
        return extractCheckResultId;
    }

    public void setExtractCheckResultId(Integer extractCheckResultId) {
        this.extractCheckResultId = extractCheckResultId;
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
    @Column(name = "violations_score")
    public Double getViolationsScore() {
        return violationsScore;
    }

    public void setViolationsScore(Double violationsScore) {
        this.violationsScore = violationsScore;
    }

    @Basic
    @Column(name = "is_error")
    public Byte getIsError() {
        return isError;
    }

    public void setIsError(Byte isError) {
        this.isError = isError;
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
        result = 31 * result + (extractCheckResultId != null ? extractCheckResultId.hashCode() : 0);
        result = 31 * result + (extractCheckAudioId != null ? extractCheckAudioId.hashCode() : 0);
        result = 31 * result + (extractCheckAudioTextId != null ? extractCheckAudioTextId.hashCode() : 0);
        result = 31 * result + (info != null ? info.hashCode() : 0);
        result = 31 * result + (violationsScore != null ? violationsScore.hashCode() : 0);
        result = 31 * result + (isError != null ? isError.hashCode() : 0);
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
        ExtractCheckResultTextEntity that = (ExtractCheckResultTextEntity) o;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (extractCheckResultId != null ? !extractCheckResultId.equals(that.extractCheckResultId) : that.extractCheckResultId != null)
            return false;
        if (extractCheckAudioId != null ? !extractCheckAudioId.equals(that.extractCheckAudioId) : that.extractCheckAudioId != null)
            return false;
        if (extractCheckAudioTextId != null ? !extractCheckAudioTextId.equals(that.extractCheckAudioTextId) : that.extractCheckAudioTextId != null)
            return false;
        if (info != null ? !info.equals(that.info) : that.info != null) return false;
        if (violationsScore != null ? !violationsScore.equals(that.violationsScore) : that.violationsScore != null)
            return false;
        if (isError != null ? !isError.equals(that.isError) : that.isError != null) return false;
        if (isDeleted != null ? !isDeleted.equals(that.isDeleted) : that.isDeleted != null) return false;
        if (createTime != null ? !createTime.equals(that.createTime) : that.createTime != null) return false;
        if (updateTime != null ? !updateTime.equals(that.updateTime) : that.updateTime != null) return false;
        if (companyId != null ? !companyId.equals(that.companyId) : that.companyId != null) return false;
        return true;
    }
}
