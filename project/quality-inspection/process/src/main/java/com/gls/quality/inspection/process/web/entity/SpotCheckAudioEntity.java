package com.gls.quality.inspection.process.web.entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "tb_spot_check_audio", schema = "ai_quality_check", catalog = "")
public class SpotCheckAudioEntity {
    private Integer id;
    private Integer spotCheckId;
    private Integer extractCheckAudioId;
    private Integer userId;
    private Byte status;
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
    @Column(name = "spot_check_id")
    public Integer getSpotCheckId() {
        return spotCheckId;
    }

    public void setSpotCheckId(Integer spotCheckId) {
        this.spotCheckId = spotCheckId;
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
    @Column(name = "user_id")
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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
        result = 31 * result + (spotCheckId != null ? spotCheckId.hashCode() : 0);
        result = 31 * result + (extractCheckAudioId != null ? extractCheckAudioId.hashCode() : 0);
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
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
        SpotCheckAudioEntity that = (SpotCheckAudioEntity) o;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (spotCheckId != null ? !spotCheckId.equals(that.spotCheckId) : that.spotCheckId != null) return false;
        if (extractCheckAudioId != null ? !extractCheckAudioId.equals(that.extractCheckAudioId) : that.extractCheckAudioId != null)
            return false;
        if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;
        if (isDeleted != null ? !isDeleted.equals(that.isDeleted) : that.isDeleted != null) return false;
        if (createTime != null ? !createTime.equals(that.createTime) : that.createTime != null) return false;
        if (updateTime != null ? !updateTime.equals(that.updateTime) : that.updateTime != null) return false;
        if (companyId != null ? !companyId.equals(that.companyId) : that.companyId != null) return false;
        return true;
    }
}
