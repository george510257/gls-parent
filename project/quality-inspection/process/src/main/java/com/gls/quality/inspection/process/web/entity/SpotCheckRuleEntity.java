package com.gls.quality.inspection.process.web.entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "tb_spot_check_rule", schema = "ai_quality_check", catalog = "")
public class SpotCheckRuleEntity {
    private Integer id;
    private Integer spotCheckId;
    private Integer spotCheckNumber;
    private Byte ruleType;
    private Integer scoreItemId;
    private Byte scoreItemType;
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
    @Column(name = "spot_check_number")
    public Integer getSpotCheckNumber() {
        return spotCheckNumber;
    }

    public void setSpotCheckNumber(Integer spotCheckNumber) {
        this.spotCheckNumber = spotCheckNumber;
    }

    @Basic
    @Column(name = "rule_type")
    public Byte getRuleType() {
        return ruleType;
    }

    public void setRuleType(Byte ruleType) {
        this.ruleType = ruleType;
    }

    @Basic
    @Column(name = "score_item_id")
    public Integer getScoreItemId() {
        return scoreItemId;
    }

    public void setScoreItemId(Integer scoreItemId) {
        this.scoreItemId = scoreItemId;
    }

    @Basic
    @Column(name = "score_item_type")
    public Byte getScoreItemType() {
        return scoreItemType;
    }

    public void setScoreItemType(Byte scoreItemType) {
        this.scoreItemType = scoreItemType;
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
        result = 31 * result + (spotCheckNumber != null ? spotCheckNumber.hashCode() : 0);
        result = 31 * result + (ruleType != null ? ruleType.hashCode() : 0);
        result = 31 * result + (scoreItemId != null ? scoreItemId.hashCode() : 0);
        result = 31 * result + (scoreItemType != null ? scoreItemType.hashCode() : 0);
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
        SpotCheckRuleEntity that = (SpotCheckRuleEntity) o;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (spotCheckId != null ? !spotCheckId.equals(that.spotCheckId) : that.spotCheckId != null) return false;
        if (spotCheckNumber != null ? !spotCheckNumber.equals(that.spotCheckNumber) : that.spotCheckNumber != null)
            return false;
        if (ruleType != null ? !ruleType.equals(that.ruleType) : that.ruleType != null) return false;
        if (scoreItemId != null ? !scoreItemId.equals(that.scoreItemId) : that.scoreItemId != null) return false;
        if (scoreItemType != null ? !scoreItemType.equals(that.scoreItemType) : that.scoreItemType != null)
            return false;
        if (isDeleted != null ? !isDeleted.equals(that.isDeleted) : that.isDeleted != null) return false;
        if (createTime != null ? !createTime.equals(that.createTime) : that.createTime != null) return false;
        if (updateTime != null ? !updateTime.equals(that.updateTime) : that.updateTime != null) return false;
        if (companyId != null ? !companyId.equals(that.companyId) : that.companyId != null) return false;
        return true;
    }
}
