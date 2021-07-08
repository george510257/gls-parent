package com.gls.quality.inspection.process.web.entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "tb_config_word_filter", schema = "ai_quality_check", catalog = "")
public class ConfigWordFilterEntity {
    private Integer id;
    private String serviceWordFilter;
    private String customerWordFilter;
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
    @Column(name = "service_word_filter")
    public String getServiceWordFilter() {
        return serviceWordFilter;
    }

    public void setServiceWordFilter(String serviceWordFilter) {
        this.serviceWordFilter = serviceWordFilter;
    }

    @Basic
    @Column(name = "customer_word_filter")
    public String getCustomerWordFilter() {
        return customerWordFilter;
    }

    public void setCustomerWordFilter(String customerWordFilter) {
        this.customerWordFilter = customerWordFilter;
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
        result = 31 * result + (serviceWordFilter != null ? serviceWordFilter.hashCode() : 0);
        result = 31 * result + (customerWordFilter != null ? customerWordFilter.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (updateTime != null ? updateTime.hashCode() : 0);
        result = 31 * result + (companyId != null ? companyId.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConfigWordFilterEntity that = (ConfigWordFilterEntity) o;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (serviceWordFilter != null ? !serviceWordFilter.equals(that.serviceWordFilter) : that.serviceWordFilter != null)
            return false;
        if (customerWordFilter != null ? !customerWordFilter.equals(that.customerWordFilter) : that.customerWordFilter != null)
            return false;
        if (createTime != null ? !createTime.equals(that.createTime) : that.createTime != null) return false;
        if (updateTime != null ? !updateTime.equals(that.updateTime) : that.updateTime != null) return false;
        if (companyId != null ? !companyId.equals(that.companyId) : that.companyId != null) return false;
        return true;
    }
}
