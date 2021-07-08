package com.gls.quality.inspection.process.web.entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "tb_extract_check_audio_text", schema = "ai_quality_check", catalog = "")
public class ExtractCheckAudioTextEntity {
    private Integer id;
    private String content;
    private String contentCorrect;
    private Integer checkRate;
    private Integer spk;
    private Byte role;
    private Integer begin;
    private Integer end;
    private Integer extractCheckAudioId;
    private Timestamp createTime;
    private Timestamp updateTime;
    private Timestamp excelTime;
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
    @Column(name = "content")
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Basic
    @Column(name = "content_correct")
    public String getContentCorrect() {
        return contentCorrect;
    }

    public void setContentCorrect(String contentCorrect) {
        this.contentCorrect = contentCorrect;
    }

    @Basic
    @Column(name = "check_rate")
    public Integer getCheckRate() {
        return checkRate;
    }

    public void setCheckRate(Integer checkRate) {
        this.checkRate = checkRate;
    }

    @Basic
    @Column(name = "spk")
    public Integer getSpk() {
        return spk;
    }

    public void setSpk(Integer spk) {
        this.spk = spk;
    }

    @Basic
    @Column(name = "role")
    public Byte getRole() {
        return role;
    }

    public void setRole(Byte role) {
        this.role = role;
    }

    @Basic
    @Column(name = "begin")
    public Integer getBegin() {
        return begin;
    }

    public void setBegin(Integer begin) {
        this.begin = begin;
    }

    @Basic
    @Column(name = "end")
    public Integer getEnd() {
        return end;
    }

    public void setEnd(Integer end) {
        this.end = end;
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
    @Column(name = "excel_time")
    public Timestamp getExcelTime() {
        return excelTime;
    }

    public void setExcelTime(Timestamp excelTime) {
        this.excelTime = excelTime;
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
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (contentCorrect != null ? contentCorrect.hashCode() : 0);
        result = 31 * result + (checkRate != null ? checkRate.hashCode() : 0);
        result = 31 * result + (spk != null ? spk.hashCode() : 0);
        result = 31 * result + (role != null ? role.hashCode() : 0);
        result = 31 * result + (begin != null ? begin.hashCode() : 0);
        result = 31 * result + (end != null ? end.hashCode() : 0);
        result = 31 * result + (extractCheckAudioId != null ? extractCheckAudioId.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (updateTime != null ? updateTime.hashCode() : 0);
        result = 31 * result + (excelTime != null ? excelTime.hashCode() : 0);
        result = 31 * result + (companyId != null ? companyId.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExtractCheckAudioTextEntity that = (ExtractCheckAudioTextEntity) o;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (content != null ? !content.equals(that.content) : that.content != null) return false;
        if (contentCorrect != null ? !contentCorrect.equals(that.contentCorrect) : that.contentCorrect != null)
            return false;
        if (checkRate != null ? !checkRate.equals(that.checkRate) : that.checkRate != null) return false;
        if (spk != null ? !spk.equals(that.spk) : that.spk != null) return false;
        if (role != null ? !role.equals(that.role) : that.role != null) return false;
        if (begin != null ? !begin.equals(that.begin) : that.begin != null) return false;
        if (end != null ? !end.equals(that.end) : that.end != null) return false;
        if (extractCheckAudioId != null ? !extractCheckAudioId.equals(that.extractCheckAudioId) : that.extractCheckAudioId != null)
            return false;
        if (createTime != null ? !createTime.equals(that.createTime) : that.createTime != null) return false;
        if (updateTime != null ? !updateTime.equals(that.updateTime) : that.updateTime != null) return false;
        if (excelTime != null ? !excelTime.equals(that.excelTime) : that.excelTime != null) return false;
        if (companyId != null ? !companyId.equals(that.companyId) : that.companyId != null) return false;
        return true;
    }
}
