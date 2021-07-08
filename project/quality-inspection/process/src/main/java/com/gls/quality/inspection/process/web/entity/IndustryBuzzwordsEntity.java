package com.gls.quality.inspection.process.web.entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "tb_industry_buzzwords", schema = "ai_quality_check", catalog = "")
public class IndustryBuzzwordsEntity {
    private Integer id;
    private Integer industryCategoryId;
    private String industryCategoryIds;
    private String buzzwordsText;
    private Integer buzzwordsNumber;
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
    @Column(name = "industry_category_id")
    public Integer getIndustryCategoryId() {
        return industryCategoryId;
    }

    public void setIndustryCategoryId(Integer industryCategoryId) {
        this.industryCategoryId = industryCategoryId;
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
    @Column(name = "buzzwords_text")
    public String getBuzzwordsText() {
        return buzzwordsText;
    }

    public void setBuzzwordsText(String buzzwordsText) {
        this.buzzwordsText = buzzwordsText;
    }

    @Basic
    @Column(name = "buzzwords_number")
    public Integer getBuzzwordsNumber() {
        return buzzwordsNumber;
    }

    public void setBuzzwordsNumber(Integer buzzwordsNumber) {
        this.buzzwordsNumber = buzzwordsNumber;
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
        result = 31 * result + (industryCategoryId != null ? industryCategoryId.hashCode() : 0);
        result = 31 * result + (industryCategoryIds != null ? industryCategoryIds.hashCode() : 0);
        result = 31 * result + (buzzwordsText != null ? buzzwordsText.hashCode() : 0);
        result = 31 * result + (buzzwordsNumber != null ? buzzwordsNumber.hashCode() : 0);
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
        IndustryBuzzwordsEntity that = (IndustryBuzzwordsEntity) o;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (industryCategoryId != null ? !industryCategoryId.equals(that.industryCategoryId) : that.industryCategoryId != null)
            return false;
        if (industryCategoryIds != null ? !industryCategoryIds.equals(that.industryCategoryIds) : that.industryCategoryIds != null)
            return false;
        if (buzzwordsText != null ? !buzzwordsText.equals(that.buzzwordsText) : that.buzzwordsText != null)
            return false;
        if (buzzwordsNumber != null ? !buzzwordsNumber.equals(that.buzzwordsNumber) : that.buzzwordsNumber != null)
            return false;
        if (isDeleted != null ? !isDeleted.equals(that.isDeleted) : that.isDeleted != null) return false;
        if (createTime != null ? !createTime.equals(that.createTime) : that.createTime != null) return false;
        if (updateTime != null ? !updateTime.equals(that.updateTime) : that.updateTime != null) return false;
        if (companyId != null ? !companyId.equals(that.companyId) : that.companyId != null) return false;
        return true;
    }
}
