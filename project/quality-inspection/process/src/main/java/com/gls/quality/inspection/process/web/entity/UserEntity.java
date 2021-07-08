package com.gls.quality.inspection.process.web.entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "tb_user", schema = "ai_quality_check", catalog = "")
public class UserEntity {
    private Integer id;
    private String username;
    private String passwordHash;
    private Byte status;
    private String portrait;
    private Byte userRole;
    private Integer userGroupingId;
    private String userGroupingIds;
    private String mobile;
    private String customerServiceId;
    private String realName;
    private Long companyId;
    private Integer createdUserId;
    private Byte isDeleted;
    private Timestamp createTime;
    private Timestamp updateTime;
    private Byte passwordStrength;
    private Byte isGrouped;

    @Id
    @Column(name = "id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "username")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Basic
    @Column(name = "password_hash")
    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    @Basic
    @Column(name = "status")
    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    @Basic
    @Column(name = "portrait")
    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    @Basic
    @Column(name = "user_role")
    public Byte getUserRole() {
        return userRole;
    }

    public void setUserRole(Byte userRole) {
        this.userRole = userRole;
    }

    @Basic
    @Column(name = "user_grouping_id")
    public Integer getUserGroupingId() {
        return userGroupingId;
    }

    public void setUserGroupingId(Integer userGroupingId) {
        this.userGroupingId = userGroupingId;
    }

    @Basic
    @Column(name = "user_grouping_ids")
    public String getUserGroupingIds() {
        return userGroupingIds;
    }

    public void setUserGroupingIds(String userGroupingIds) {
        this.userGroupingIds = userGroupingIds;
    }

    @Basic
    @Column(name = "mobile")
    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Basic
    @Column(name = "customer_service_id")
    public String getCustomerServiceId() {
        return customerServiceId;
    }

    public void setCustomerServiceId(String customerServiceId) {
        this.customerServiceId = customerServiceId;
    }

    @Basic
    @Column(name = "real_name")
    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
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
    @Column(name = "created_user_id")
    public Integer getCreatedUserId() {
        return createdUserId;
    }

    public void setCreatedUserId(Integer createdUserId) {
        this.createdUserId = createdUserId;
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
    @Column(name = "password_strength")
    public Byte getPasswordStrength() {
        return passwordStrength;
    }

    public void setPasswordStrength(Byte passwordStrength) {
        this.passwordStrength = passwordStrength;
    }

    @Basic
    @Column(name = "is_grouped")
    public Byte getIsGrouped() {
        return isGrouped;
    }

    public void setIsGrouped(Byte isGrouped) {
        this.isGrouped = isGrouped;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (passwordHash != null ? passwordHash.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (portrait != null ? portrait.hashCode() : 0);
        result = 31 * result + (userRole != null ? userRole.hashCode() : 0);
        result = 31 * result + (userGroupingId != null ? userGroupingId.hashCode() : 0);
        result = 31 * result + (userGroupingIds != null ? userGroupingIds.hashCode() : 0);
        result = 31 * result + (mobile != null ? mobile.hashCode() : 0);
        result = 31 * result + (customerServiceId != null ? customerServiceId.hashCode() : 0);
        result = 31 * result + (realName != null ? realName.hashCode() : 0);
        result = 31 * result + (companyId != null ? companyId.hashCode() : 0);
        result = 31 * result + (createdUserId != null ? createdUserId.hashCode() : 0);
        result = 31 * result + (isDeleted != null ? isDeleted.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (updateTime != null ? updateTime.hashCode() : 0);
        result = 31 * result + (passwordStrength != null ? passwordStrength.hashCode() : 0);
        result = 31 * result + (isGrouped != null ? isGrouped.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity that = (UserEntity) o;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (username != null ? !username.equals(that.username) : that.username != null) return false;
        if (passwordHash != null ? !passwordHash.equals(that.passwordHash) : that.passwordHash != null) return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;
        if (portrait != null ? !portrait.equals(that.portrait) : that.portrait != null) return false;
        if (userRole != null ? !userRole.equals(that.userRole) : that.userRole != null) return false;
        if (userGroupingId != null ? !userGroupingId.equals(that.userGroupingId) : that.userGroupingId != null)
            return false;
        if (userGroupingIds != null ? !userGroupingIds.equals(that.userGroupingIds) : that.userGroupingIds != null)
            return false;
        if (mobile != null ? !mobile.equals(that.mobile) : that.mobile != null) return false;
        if (customerServiceId != null ? !customerServiceId.equals(that.customerServiceId) : that.customerServiceId != null)
            return false;
        if (realName != null ? !realName.equals(that.realName) : that.realName != null) return false;
        if (companyId != null ? !companyId.equals(that.companyId) : that.companyId != null) return false;
        if (createdUserId != null ? !createdUserId.equals(that.createdUserId) : that.createdUserId != null)
            return false;
        if (isDeleted != null ? !isDeleted.equals(that.isDeleted) : that.isDeleted != null) return false;
        if (createTime != null ? !createTime.equals(that.createTime) : that.createTime != null) return false;
        if (updateTime != null ? !updateTime.equals(that.updateTime) : that.updateTime != null) return false;
        if (passwordStrength != null ? !passwordStrength.equals(that.passwordStrength) : that.passwordStrength != null)
            return false;
        if (isGrouped != null ? !isGrouped.equals(that.isGrouped) : that.isGrouped != null) return false;
        return true;
    }
}
