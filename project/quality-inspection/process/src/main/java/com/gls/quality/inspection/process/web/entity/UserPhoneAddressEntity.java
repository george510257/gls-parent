package com.gls.quality.inspection.process.web.entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "tb_user_phone_address", schema = "ai_quality_check", catalog = "")
public class UserPhoneAddressEntity {
    private Integer id;
    private Integer extractCheckAudioId;
    private String address;
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
    @Column(name = "extract_check_audio_id")
    public Integer getExtractCheckAudioId() {
        return extractCheckAudioId;
    }

    public void setExtractCheckAudioId(Integer extractCheckAudioId) {
        this.extractCheckAudioId = extractCheckAudioId;
    }

    @Basic
    @Column(name = "address")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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
        result = 31 * result + (extractCheckAudioId != null ? extractCheckAudioId.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (companyId != null ? companyId.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserPhoneAddressEntity that = (UserPhoneAddressEntity) o;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (extractCheckAudioId != null ? !extractCheckAudioId.equals(that.extractCheckAudioId) : that.extractCheckAudioId != null)
            return false;
        if (address != null ? !address.equals(that.address) : that.address != null) return false;
        if (createTime != null ? !createTime.equals(that.createTime) : that.createTime != null) return false;
        if (companyId != null ? !companyId.equals(that.companyId) : that.companyId != null) return false;
        return true;
    }
}
