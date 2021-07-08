package com.gls.quality.inspection.process.web.entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "special_label", schema = "ai_quality_check", catalog = "")
public class SpecialLabelEntity {
    private Integer id;
    private Long companyId;
    private Integer modelId;
    private String name;
    private Integer type;
    private String callIds;
    private Integer status;
    private String childLabel;
    private String digest;
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
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "type")
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Basic
    @Column(name = "call_ids")
    public String getCallIds() {
        return callIds;
    }

    public void setCallIds(String callIds) {
        this.callIds = callIds;
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
    @Column(name = "child_label")
    public String getChildLabel() {
        return childLabel;
    }

    public void setChildLabel(String childLabel) {
        this.childLabel = childLabel;
    }

    @Basic
    @Column(name = "digest")
    public String getDigest() {
        return digest;
    }

    public void setDigest(String digest) {
        this.digest = digest;
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
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (callIds != null ? callIds.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (childLabel != null ? childLabel.hashCode() : 0);
        result = 31 * result + (digest != null ? digest.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (updateTime != null ? updateTime.hashCode() : 0);
        result = 31 * result + (deleteTime != null ? deleteTime.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SpecialLabelEntity that = (SpecialLabelEntity) o;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (companyId != null ? !companyId.equals(that.companyId) : that.companyId != null) return false;
        if (modelId != null ? !modelId.equals(that.modelId) : that.modelId != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (type != null ? !type.equals(that.type) : that.type != null) return false;
        if (callIds != null ? !callIds.equals(that.callIds) : that.callIds != null) return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;
        if (childLabel != null ? !childLabel.equals(that.childLabel) : that.childLabel != null) return false;
        if (digest != null ? !digest.equals(that.digest) : that.digest != null) return false;
        if (createTime != null ? !createTime.equals(that.createTime) : that.createTime != null) return false;
        if (updateTime != null ? !updateTime.equals(that.updateTime) : that.updateTime != null) return false;
        if (deleteTime != null ? !deleteTime.equals(that.deleteTime) : that.deleteTime != null) return false;
        return true;
    }
}
