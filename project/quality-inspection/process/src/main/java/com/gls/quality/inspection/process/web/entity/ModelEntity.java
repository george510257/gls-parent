package com.gls.quality.inspection.process.web.entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "model", schema = "ai_quality_check", catalog = "")
public class ModelEntity {
    private Integer id;
    private Long companyId;
    private String name;
    private Integer industryCategoryId;
    private String industryCategory;
    private Integer status;
    private Integer released;
    private Integer createBy;
    private Integer voiceTimeout;
    private Integer voiceShutdown;
    private Integer voiceFast;
    private Integer voiceDb;
    private Integer voiceInterrupt;
    private Byte voiceInterruptStatus;
    private Integer labelScore;
    private Timestamp createTime;
    private Timestamp updateTime;
    private Timestamp deleteTime;
    private Byte tokenModule;
    private Byte semanticsModule;

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
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
    @Column(name = "industry_category")
    public String getIndustryCategory() {
        return industryCategory;
    }

    public void setIndustryCategory(String industryCategory) {
        this.industryCategory = industryCategory;
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
    @Column(name = "released")
    public Integer getReleased() {
        return released;
    }

    public void setReleased(Integer released) {
        this.released = released;
    }

    @Basic
    @Column(name = "create_by")
    public Integer getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Integer createBy) {
        this.createBy = createBy;
    }

    @Basic
    @Column(name = "voice_timeout")
    public Integer getVoiceTimeout() {
        return voiceTimeout;
    }

    public void setVoiceTimeout(Integer voiceTimeout) {
        this.voiceTimeout = voiceTimeout;
    }

    @Basic
    @Column(name = "voice_shutdown")
    public Integer getVoiceShutdown() {
        return voiceShutdown;
    }

    public void setVoiceShutdown(Integer voiceShutdown) {
        this.voiceShutdown = voiceShutdown;
    }

    @Basic
    @Column(name = "voice_fast")
    public Integer getVoiceFast() {
        return voiceFast;
    }

    public void setVoiceFast(Integer voiceFast) {
        this.voiceFast = voiceFast;
    }

    @Basic
    @Column(name = "voice_db")
    public Integer getVoiceDb() {
        return voiceDb;
    }

    public void setVoiceDb(Integer voiceDb) {
        this.voiceDb = voiceDb;
    }

    @Basic
    @Column(name = "voice_interrupt")
    public Integer getVoiceInterrupt() {
        return voiceInterrupt;
    }

    public void setVoiceInterrupt(Integer voiceInterrupt) {
        this.voiceInterrupt = voiceInterrupt;
    }

    @Basic
    @Column(name = "voice_interrupt_status")
    public Byte getVoiceInterruptStatus() {
        return voiceInterruptStatus;
    }

    public void setVoiceInterruptStatus(Byte voiceInterruptStatus) {
        this.voiceInterruptStatus = voiceInterruptStatus;
    }

    @Basic
    @Column(name = "label_score")
    public Integer getLabelScore() {
        return labelScore;
    }

    public void setLabelScore(Integer labelScore) {
        this.labelScore = labelScore;
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

    @Basic
    @Column(name = "token_module")
    public Byte getTokenModule() {
        return tokenModule;
    }

    public void setTokenModule(Byte tokenModule) {
        this.tokenModule = tokenModule;
    }

    @Basic
    @Column(name = "semantics_module")
    public Byte getSemanticsModule() {
        return semanticsModule;
    }

    public void setSemanticsModule(Byte semanticsModule) {
        this.semanticsModule = semanticsModule;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (companyId != null ? companyId.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (industryCategoryId != null ? industryCategoryId.hashCode() : 0);
        result = 31 * result + (industryCategory != null ? industryCategory.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (released != null ? released.hashCode() : 0);
        result = 31 * result + (createBy != null ? createBy.hashCode() : 0);
        result = 31 * result + (voiceTimeout != null ? voiceTimeout.hashCode() : 0);
        result = 31 * result + (voiceShutdown != null ? voiceShutdown.hashCode() : 0);
        result = 31 * result + (voiceFast != null ? voiceFast.hashCode() : 0);
        result = 31 * result + (voiceDb != null ? voiceDb.hashCode() : 0);
        result = 31 * result + (voiceInterrupt != null ? voiceInterrupt.hashCode() : 0);
        result = 31 * result + (voiceInterruptStatus != null ? voiceInterruptStatus.hashCode() : 0);
        result = 31 * result + (labelScore != null ? labelScore.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (updateTime != null ? updateTime.hashCode() : 0);
        result = 31 * result + (deleteTime != null ? deleteTime.hashCode() : 0);
        result = 31 * result + (tokenModule != null ? tokenModule.hashCode() : 0);
        result = 31 * result + (semanticsModule != null ? semanticsModule.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ModelEntity that = (ModelEntity) o;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (companyId != null ? !companyId.equals(that.companyId) : that.companyId != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (industryCategoryId != null ? !industryCategoryId.equals(that.industryCategoryId) : that.industryCategoryId != null)
            return false;
        if (industryCategory != null ? !industryCategory.equals(that.industryCategory) : that.industryCategory != null)
            return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;
        if (released != null ? !released.equals(that.released) : that.released != null) return false;
        if (createBy != null ? !createBy.equals(that.createBy) : that.createBy != null) return false;
        if (voiceTimeout != null ? !voiceTimeout.equals(that.voiceTimeout) : that.voiceTimeout != null) return false;
        if (voiceShutdown != null ? !voiceShutdown.equals(that.voiceShutdown) : that.voiceShutdown != null)
            return false;
        if (voiceFast != null ? !voiceFast.equals(that.voiceFast) : that.voiceFast != null) return false;
        if (voiceDb != null ? !voiceDb.equals(that.voiceDb) : that.voiceDb != null) return false;
        if (voiceInterrupt != null ? !voiceInterrupt.equals(that.voiceInterrupt) : that.voiceInterrupt != null)
            return false;
        if (voiceInterruptStatus != null ? !voiceInterruptStatus.equals(that.voiceInterruptStatus) : that.voiceInterruptStatus != null)
            return false;
        if (labelScore != null ? !labelScore.equals(that.labelScore) : that.labelScore != null) return false;
        if (createTime != null ? !createTime.equals(that.createTime) : that.createTime != null) return false;
        if (updateTime != null ? !updateTime.equals(that.updateTime) : that.updateTime != null) return false;
        if (deleteTime != null ? !deleteTime.equals(that.deleteTime) : that.deleteTime != null) return false;
        if (tokenModule != null ? !tokenModule.equals(that.tokenModule) : that.tokenModule != null) return false;
        if (semanticsModule != null ? !semanticsModule.equals(that.semanticsModule) : that.semanticsModule != null)
            return false;
        return true;
    }
}
