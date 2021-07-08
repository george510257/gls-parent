package com.gls.quality.inspection.process.web.entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "total_test_task", schema = "ai_quality_check", catalog = "")
public class TotalTestTaskEntity {
    private Integer id;
    private Long companyId;
    private Integer userId;
    private Integer modelId;
    private String testName;
    private Byte testType;
    private Integer status;
    private Double passRate;
    private Timestamp createTime;
    private Byte isDeleted;

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
    @Column(name = "user_id")
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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
    @Column(name = "test_name")
    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    @Basic
    @Column(name = "test_type")
    public Byte getTestType() {
        return testType;
    }

    public void setTestType(Byte testType) {
        this.testType = testType;
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
    @Column(name = "pass_rate")
    public Double getPassRate() {
        return passRate;
    }

    public void setPassRate(Double passRate) {
        this.passRate = passRate;
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
    @Column(name = "is_deleted")
    public Byte getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Byte isDeleted) {
        this.isDeleted = isDeleted;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (companyId != null ? companyId.hashCode() : 0);
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + (modelId != null ? modelId.hashCode() : 0);
        result = 31 * result + (testName != null ? testName.hashCode() : 0);
        result = 31 * result + (testType != null ? testType.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (passRate != null ? passRate.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (isDeleted != null ? isDeleted.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TotalTestTaskEntity that = (TotalTestTaskEntity) o;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (companyId != null ? !companyId.equals(that.companyId) : that.companyId != null) return false;
        if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;
        if (modelId != null ? !modelId.equals(that.modelId) : that.modelId != null) return false;
        if (testName != null ? !testName.equals(that.testName) : that.testName != null) return false;
        if (testType != null ? !testType.equals(that.testType) : that.testType != null) return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;
        if (passRate != null ? !passRate.equals(that.passRate) : that.passRate != null) return false;
        if (createTime != null ? !createTime.equals(that.createTime) : that.createTime != null) return false;
        if (isDeleted != null ? !isDeleted.equals(that.isDeleted) : that.isDeleted != null) return false;
        return true;
    }
}
