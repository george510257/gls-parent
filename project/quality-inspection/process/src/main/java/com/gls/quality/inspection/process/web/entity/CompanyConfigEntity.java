package com.gls.quality.inspection.process.web.entity;

import javax.persistence.*;

@Entity
@Table(name = "company_config", schema = "ai_quality_check", catalog = "")
public class CompanyConfigEntity {
    private Integer id;
    private Long companyId;
    private String imgLogo;
    private String imgCover;
    private Byte translateConcurrent;
    private Byte modelNum;
    private Byte adminNum;
    private Byte customerServiceNum;
    private Byte inspectorNum;

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
    @Column(name = "img_logo")
    public String getImgLogo() {
        return imgLogo;
    }

    public void setImgLogo(String imgLogo) {
        this.imgLogo = imgLogo;
    }

    @Basic
    @Column(name = "img_cover")
    public String getImgCover() {
        return imgCover;
    }

    public void setImgCover(String imgCover) {
        this.imgCover = imgCover;
    }

    @Basic
    @Column(name = "translate_concurrent")
    public Byte getTranslateConcurrent() {
        return translateConcurrent;
    }

    public void setTranslateConcurrent(Byte translateConcurrent) {
        this.translateConcurrent = translateConcurrent;
    }

    @Basic
    @Column(name = "model_num")
    public Byte getModelNum() {
        return modelNum;
    }

    public void setModelNum(Byte modelNum) {
        this.modelNum = modelNum;
    }

    @Basic
    @Column(name = "admin_num")
    public Byte getAdminNum() {
        return adminNum;
    }

    public void setAdminNum(Byte adminNum) {
        this.adminNum = adminNum;
    }

    @Basic
    @Column(name = "customer_service_num")
    public Byte getCustomerServiceNum() {
        return customerServiceNum;
    }

    public void setCustomerServiceNum(Byte customerServiceNum) {
        this.customerServiceNum = customerServiceNum;
    }

    @Basic
    @Column(name = "inspector_num")
    public Byte getInspectorNum() {
        return inspectorNum;
    }

    public void setInspectorNum(Byte inspectorNum) {
        this.inspectorNum = inspectorNum;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (companyId != null ? companyId.hashCode() : 0);
        result = 31 * result + (imgLogo != null ? imgLogo.hashCode() : 0);
        result = 31 * result + (imgCover != null ? imgCover.hashCode() : 0);
        result = 31 * result + (translateConcurrent != null ? translateConcurrent.hashCode() : 0);
        result = 31 * result + (modelNum != null ? modelNum.hashCode() : 0);
        result = 31 * result + (adminNum != null ? adminNum.hashCode() : 0);
        result = 31 * result + (customerServiceNum != null ? customerServiceNum.hashCode() : 0);
        result = 31 * result + (inspectorNum != null ? inspectorNum.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CompanyConfigEntity that = (CompanyConfigEntity) o;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (companyId != null ? !companyId.equals(that.companyId) : that.companyId != null) return false;
        if (imgLogo != null ? !imgLogo.equals(that.imgLogo) : that.imgLogo != null) return false;
        if (imgCover != null ? !imgCover.equals(that.imgCover) : that.imgCover != null) return false;
        if (translateConcurrent != null ? !translateConcurrent.equals(that.translateConcurrent) : that.translateConcurrent != null)
            return false;
        if (modelNum != null ? !modelNum.equals(that.modelNum) : that.modelNum != null) return false;
        if (adminNum != null ? !adminNum.equals(that.adminNum) : that.adminNum != null) return false;
        if (customerServiceNum != null ? !customerServiceNum.equals(that.customerServiceNum) : that.customerServiceNum != null)
            return false;
        if (inspectorNum != null ? !inspectorNum.equals(that.inspectorNum) : that.inspectorNum != null) return false;
        return true;
    }
}
