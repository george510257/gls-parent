package com.gls.quality.inspection.process.web.entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "corpus_audio_tranfor", schema = "ai_quality_check", catalog = "")
public class CorpusAudioTranforEntity {
    private Integer id;
    private Integer corpusId;
    private Integer lid;
    private Integer begin;
    private Integer end;
    private String onebest;
    private Double sc;
    private Integer spk;
    private Timestamp createTime;
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
    @Column(name = "corpus_id")
    public Integer getCorpusId() {
        return corpusId;
    }

    public void setCorpusId(Integer corpusId) {
        this.corpusId = corpusId;
    }

    @Basic
    @Column(name = "lid")
    public Integer getLid() {
        return lid;
    }

    public void setLid(Integer lid) {
        this.lid = lid;
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
    @Column(name = "onebest")
    public String getOnebest() {
        return onebest;
    }

    public void setOnebest(String onebest) {
        this.onebest = onebest;
    }

    @Basic
    @Column(name = "sc")
    public Double getSc() {
        return sc;
    }

    public void setSc(Double sc) {
        this.sc = sc;
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
    @Column(name = "create_time")
    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
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
        result = 31 * result + (corpusId != null ? corpusId.hashCode() : 0);
        result = 31 * result + (lid != null ? lid.hashCode() : 0);
        result = 31 * result + (begin != null ? begin.hashCode() : 0);
        result = 31 * result + (end != null ? end.hashCode() : 0);
        result = 31 * result + (onebest != null ? onebest.hashCode() : 0);
        result = 31 * result + (sc != null ? sc.hashCode() : 0);
        result = 31 * result + (spk != null ? spk.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (companyId != null ? companyId.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CorpusAudioTranforEntity that = (CorpusAudioTranforEntity) o;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (corpusId != null ? !corpusId.equals(that.corpusId) : that.corpusId != null) return false;
        if (lid != null ? !lid.equals(that.lid) : that.lid != null) return false;
        if (begin != null ? !begin.equals(that.begin) : that.begin != null) return false;
        if (end != null ? !end.equals(that.end) : that.end != null) return false;
        if (onebest != null ? !onebest.equals(that.onebest) : that.onebest != null) return false;
        if (sc != null ? !sc.equals(that.sc) : that.sc != null) return false;
        if (spk != null ? !spk.equals(that.spk) : that.spk != null) return false;
        if (createTime != null ? !createTime.equals(that.createTime) : that.createTime != null) return false;
        if (companyId != null ? !companyId.equals(that.companyId) : that.companyId != null) return false;
        return true;
    }
}
