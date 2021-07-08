package com.gls.quality.inspection.process.web.entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "tb_config_invalid_audio", schema = "ai_quality_check", catalog = "")
public class ConfigInvalidAudioEntity {
    private Integer id;
    private Byte defineDurationRadio;
    private Integer defineDurationSecond;
    private Byte defineResultRadio;
    private Byte defineRoundRadio;
    private Integer defineRoundCount;
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
    @Column(name = "define_duration_radio")
    public Byte getDefineDurationRadio() {
        return defineDurationRadio;
    }

    public void setDefineDurationRadio(Byte defineDurationRadio) {
        this.defineDurationRadio = defineDurationRadio;
    }

    @Basic
    @Column(name = "define_duration_second")
    public Integer getDefineDurationSecond() {
        return defineDurationSecond;
    }

    public void setDefineDurationSecond(Integer defineDurationSecond) {
        this.defineDurationSecond = defineDurationSecond;
    }

    @Basic
    @Column(name = "define_result_radio")
    public Byte getDefineResultRadio() {
        return defineResultRadio;
    }

    public void setDefineResultRadio(Byte defineResultRadio) {
        this.defineResultRadio = defineResultRadio;
    }

    @Basic
    @Column(name = "define_round_radio")
    public Byte getDefineRoundRadio() {
        return defineRoundRadio;
    }

    public void setDefineRoundRadio(Byte defineRoundRadio) {
        this.defineRoundRadio = defineRoundRadio;
    }

    @Basic
    @Column(name = "define_round_count")
    public Integer getDefineRoundCount() {
        return defineRoundCount;
    }

    public void setDefineRoundCount(Integer defineRoundCount) {
        this.defineRoundCount = defineRoundCount;
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
        result = 31 * result + (defineDurationRadio != null ? defineDurationRadio.hashCode() : 0);
        result = 31 * result + (defineDurationSecond != null ? defineDurationSecond.hashCode() : 0);
        result = 31 * result + (defineResultRadio != null ? defineResultRadio.hashCode() : 0);
        result = 31 * result + (defineRoundRadio != null ? defineRoundRadio.hashCode() : 0);
        result = 31 * result + (defineRoundCount != null ? defineRoundCount.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (updateTime != null ? updateTime.hashCode() : 0);
        result = 31 * result + (companyId != null ? companyId.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConfigInvalidAudioEntity that = (ConfigInvalidAudioEntity) o;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (defineDurationRadio != null ? !defineDurationRadio.equals(that.defineDurationRadio) : that.defineDurationRadio != null)
            return false;
        if (defineDurationSecond != null ? !defineDurationSecond.equals(that.defineDurationSecond) : that.defineDurationSecond != null)
            return false;
        if (defineResultRadio != null ? !defineResultRadio.equals(that.defineResultRadio) : that.defineResultRadio != null)
            return false;
        if (defineRoundRadio != null ? !defineRoundRadio.equals(that.defineRoundRadio) : that.defineRoundRadio != null)
            return false;
        if (defineRoundCount != null ? !defineRoundCount.equals(that.defineRoundCount) : that.defineRoundCount != null)
            return false;
        if (createTime != null ? !createTime.equals(that.createTime) : that.createTime != null) return false;
        if (updateTime != null ? !updateTime.equals(that.updateTime) : that.updateTime != null) return false;
        if (companyId != null ? !companyId.equals(that.companyId) : that.companyId != null) return false;
        return true;
    }
}
