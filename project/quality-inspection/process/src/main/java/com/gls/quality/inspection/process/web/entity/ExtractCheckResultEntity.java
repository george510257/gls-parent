package com.gls.quality.inspection.process.web.entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "tb_extract_check_result", schema = "ai_quality_check", catalog = "")
public class ExtractCheckResultEntity {
    private Integer id;
    private Integer extractCheckAudioId;
    private String violationsItem;
    private Double violationsScore;
    private String category;
    private String paragraph;
    private Integer scoreItemId;
    private Integer scoreItemType;
    private Byte scoreAttribute;
    private Byte inspectionObject;
    private String keyInfo;
    private Byte type;
    private Byte recheckStatus;
    private String representation;
    private Byte isMissed;
    private Timestamp createTime;
    private Timestamp updateTime;
    private Byte isDeleted;
    private Byte isError;
    private Long companyId;
    private String scoreStrategy;

    @Id
    @Column(name = "id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
    @Column(name = "violations_item")
    public String getViolationsItem() {
        return violationsItem;
    }

    public void setViolationsItem(String violationsItem) {
        this.violationsItem = violationsItem;
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
    @Column(name = "category")
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Basic
    @Column(name = "paragraph")
    public String getParagraph() {
        return paragraph;
    }

    public void setParagraph(String paragraph) {
        this.paragraph = paragraph;
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
    public Integer getScoreItemType() {
        return scoreItemType;
    }

    public void setScoreItemType(Integer scoreItemType) {
        this.scoreItemType = scoreItemType;
    }

    @Basic
    @Column(name = "score_attribute")
    public Byte getScoreAttribute() {
        return scoreAttribute;
    }

    public void setScoreAttribute(Byte scoreAttribute) {
        this.scoreAttribute = scoreAttribute;
    }

    @Basic
    @Column(name = "inspection_object")
    public Byte getInspectionObject() {
        return inspectionObject;
    }

    public void setInspectionObject(Byte inspectionObject) {
        this.inspectionObject = inspectionObject;
    }

    @Basic
    @Column(name = "key_info")
    public String getKeyInfo() {
        return keyInfo;
    }

    public void setKeyInfo(String keyInfo) {
        this.keyInfo = keyInfo;
    }

    @Basic
    @Column(name = "type")
    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    @Basic
    @Column(name = "recheck_status")
    public Byte getRecheckStatus() {
        return recheckStatus;
    }

    public void setRecheckStatus(Byte recheckStatus) {
        this.recheckStatus = recheckStatus;
    }

    @Basic
    @Column(name = "representation")
    public String getRepresentation() {
        return representation;
    }

    public void setRepresentation(String representation) {
        this.representation = representation;
    }

    @Basic
    @Column(name = "is_missed")
    public Byte getIsMissed() {
        return isMissed;
    }

    public void setIsMissed(Byte isMissed) {
        this.isMissed = isMissed;
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
    @Column(name = "is_deleted")
    public Byte getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Byte isDeleted) {
        this.isDeleted = isDeleted;
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
    @Column(name = "company_id")
    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    @Basic
    @Column(name = "score_strategy")
    public String getScoreStrategy() {
        return scoreStrategy;
    }

    public void setScoreStrategy(String scoreStrategy) {
        this.scoreStrategy = scoreStrategy;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (extractCheckAudioId != null ? extractCheckAudioId.hashCode() : 0);
        result = 31 * result + (violationsItem != null ? violationsItem.hashCode() : 0);
        result = 31 * result + (violationsScore != null ? violationsScore.hashCode() : 0);
        result = 31 * result + (category != null ? category.hashCode() : 0);
        result = 31 * result + (paragraph != null ? paragraph.hashCode() : 0);
        result = 31 * result + (scoreItemId != null ? scoreItemId.hashCode() : 0);
        result = 31 * result + (scoreItemType != null ? scoreItemType.hashCode() : 0);
        result = 31 * result + (scoreAttribute != null ? scoreAttribute.hashCode() : 0);
        result = 31 * result + (inspectionObject != null ? inspectionObject.hashCode() : 0);
        result = 31 * result + (keyInfo != null ? keyInfo.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (recheckStatus != null ? recheckStatus.hashCode() : 0);
        result = 31 * result + (representation != null ? representation.hashCode() : 0);
        result = 31 * result + (isMissed != null ? isMissed.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (updateTime != null ? updateTime.hashCode() : 0);
        result = 31 * result + (isDeleted != null ? isDeleted.hashCode() : 0);
        result = 31 * result + (isError != null ? isError.hashCode() : 0);
        result = 31 * result + (companyId != null ? companyId.hashCode() : 0);
        result = 31 * result + (scoreStrategy != null ? scoreStrategy.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExtractCheckResultEntity that = (ExtractCheckResultEntity) o;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (extractCheckAudioId != null ? !extractCheckAudioId.equals(that.extractCheckAudioId) : that.extractCheckAudioId != null)
            return false;
        if (violationsItem != null ? !violationsItem.equals(that.violationsItem) : that.violationsItem != null)
            return false;
        if (violationsScore != null ? !violationsScore.equals(that.violationsScore) : that.violationsScore != null)
            return false;
        if (category != null ? !category.equals(that.category) : that.category != null) return false;
        if (paragraph != null ? !paragraph.equals(that.paragraph) : that.paragraph != null) return false;
        if (scoreItemId != null ? !scoreItemId.equals(that.scoreItemId) : that.scoreItemId != null) return false;
        if (scoreItemType != null ? !scoreItemType.equals(that.scoreItemType) : that.scoreItemType != null)
            return false;
        if (scoreAttribute != null ? !scoreAttribute.equals(that.scoreAttribute) : that.scoreAttribute != null)
            return false;
        if (inspectionObject != null ? !inspectionObject.equals(that.inspectionObject) : that.inspectionObject != null)
            return false;
        if (keyInfo != null ? !keyInfo.equals(that.keyInfo) : that.keyInfo != null) return false;
        if (type != null ? !type.equals(that.type) : that.type != null) return false;
        if (recheckStatus != null ? !recheckStatus.equals(that.recheckStatus) : that.recheckStatus != null)
            return false;
        if (representation != null ? !representation.equals(that.representation) : that.representation != null)
            return false;
        if (isMissed != null ? !isMissed.equals(that.isMissed) : that.isMissed != null) return false;
        if (createTime != null ? !createTime.equals(that.createTime) : that.createTime != null) return false;
        if (updateTime != null ? !updateTime.equals(that.updateTime) : that.updateTime != null) return false;
        if (isDeleted != null ? !isDeleted.equals(that.isDeleted) : that.isDeleted != null) return false;
        if (isError != null ? !isError.equals(that.isError) : that.isError != null) return false;
        if (companyId != null ? !companyId.equals(that.companyId) : that.companyId != null) return false;
        if (scoreStrategy != null ? !scoreStrategy.equals(that.scoreStrategy) : that.scoreStrategy != null)
            return false;
        return true;
    }
}
