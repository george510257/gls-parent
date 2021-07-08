package com.gls.quality.inspection.process.web.entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "tb_config_channel", schema = "ai_quality_check", catalog = "")
public class ConfigChannelEntity {
    private Integer id;
    private Byte doubleChannelRadio;
    private Byte doubleChannelDefault;
    private Byte singleChannelRadio;
    private String singleChannelText;
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
    @Column(name = "double_channel_radio")
    public Byte getDoubleChannelRadio() {
        return doubleChannelRadio;
    }

    public void setDoubleChannelRadio(Byte doubleChannelRadio) {
        this.doubleChannelRadio = doubleChannelRadio;
    }

    @Basic
    @Column(name = "double_channel_default")
    public Byte getDoubleChannelDefault() {
        return doubleChannelDefault;
    }

    public void setDoubleChannelDefault(Byte doubleChannelDefault) {
        this.doubleChannelDefault = doubleChannelDefault;
    }

    @Basic
    @Column(name = "single_channel_radio")
    public Byte getSingleChannelRadio() {
        return singleChannelRadio;
    }

    public void setSingleChannelRadio(Byte singleChannelRadio) {
        this.singleChannelRadio = singleChannelRadio;
    }

    @Basic
    @Column(name = "single_channel_text")
    public String getSingleChannelText() {
        return singleChannelText;
    }

    public void setSingleChannelText(String singleChannelText) {
        this.singleChannelText = singleChannelText;
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
        result = 31 * result + (doubleChannelRadio != null ? doubleChannelRadio.hashCode() : 0);
        result = 31 * result + (doubleChannelDefault != null ? doubleChannelDefault.hashCode() : 0);
        result = 31 * result + (singleChannelRadio != null ? singleChannelRadio.hashCode() : 0);
        result = 31 * result + (singleChannelText != null ? singleChannelText.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (updateTime != null ? updateTime.hashCode() : 0);
        result = 31 * result + (companyId != null ? companyId.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConfigChannelEntity that = (ConfigChannelEntity) o;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (doubleChannelRadio != null ? !doubleChannelRadio.equals(that.doubleChannelRadio) : that.doubleChannelRadio != null)
            return false;
        if (doubleChannelDefault != null ? !doubleChannelDefault.equals(that.doubleChannelDefault) : that.doubleChannelDefault != null)
            return false;
        if (singleChannelRadio != null ? !singleChannelRadio.equals(that.singleChannelRadio) : that.singleChannelRadio != null)
            return false;
        if (singleChannelText != null ? !singleChannelText.equals(that.singleChannelText) : that.singleChannelText != null)
            return false;
        if (createTime != null ? !createTime.equals(that.createTime) : that.createTime != null) return false;
        if (updateTime != null ? !updateTime.equals(that.updateTime) : that.updateTime != null) return false;
        if (companyId != null ? !companyId.equals(that.companyId) : that.companyId != null) return false;
        return true;
    }
}
