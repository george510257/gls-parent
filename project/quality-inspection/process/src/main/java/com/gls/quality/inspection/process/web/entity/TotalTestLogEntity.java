package com.gls.quality.inspection.process.web.entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "total_test_log", schema = "ai_quality_check", catalog = "")
public class TotalTestLogEntity {
    private Integer id;
    private Integer totalTestTaskId;
    private String testContent;
    private String expectLabel;
    private Byte expectRole;
    private String recLabel;
    private String recRule;
    private Byte recRole;
    private String module;
    private Double score;
    private Integer pass;
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
    @Column(name = "total_test_task_id")
    public Integer getTotalTestTaskId() {
        return totalTestTaskId;
    }

    public void setTotalTestTaskId(Integer totalTestTaskId) {
        this.totalTestTaskId = totalTestTaskId;
    }

    @Basic
    @Column(name = "test_content")
    public String getTestContent() {
        return testContent;
    }

    public void setTestContent(String testContent) {
        this.testContent = testContent;
    }

    @Basic
    @Column(name = "expect_label")
    public String getExpectLabel() {
        return expectLabel;
    }

    public void setExpectLabel(String expectLabel) {
        this.expectLabel = expectLabel;
    }

    @Basic
    @Column(name = "expect_role")
    public Byte getExpectRole() {
        return expectRole;
    }

    public void setExpectRole(Byte expectRole) {
        this.expectRole = expectRole;
    }

    @Basic
    @Column(name = "rec_label")
    public String getRecLabel() {
        return recLabel;
    }

    public void setRecLabel(String recLabel) {
        this.recLabel = recLabel;
    }

    @Basic
    @Column(name = "rec_rule")
    public String getRecRule() {
        return recRule;
    }

    public void setRecRule(String recRule) {
        this.recRule = recRule;
    }

    @Basic
    @Column(name = "rec_role")
    public Byte getRecRole() {
        return recRole;
    }

    public void setRecRole(Byte recRole) {
        this.recRole = recRole;
    }

    @Basic
    @Column(name = "module")
    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    @Basic
    @Column(name = "score")
    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    @Basic
    @Column(name = "pass")
    public Integer getPass() {
        return pass;
    }

    public void setPass(Integer pass) {
        this.pass = pass;
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
        result = 31 * result + (totalTestTaskId != null ? totalTestTaskId.hashCode() : 0);
        result = 31 * result + (testContent != null ? testContent.hashCode() : 0);
        result = 31 * result + (expectLabel != null ? expectLabel.hashCode() : 0);
        result = 31 * result + (expectRole != null ? expectRole.hashCode() : 0);
        result = 31 * result + (recLabel != null ? recLabel.hashCode() : 0);
        result = 31 * result + (recRule != null ? recRule.hashCode() : 0);
        result = 31 * result + (recRole != null ? recRole.hashCode() : 0);
        result = 31 * result + (module != null ? module.hashCode() : 0);
        result = 31 * result + (score != null ? score.hashCode() : 0);
        result = 31 * result + (pass != null ? pass.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (companyId != null ? companyId.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TotalTestLogEntity that = (TotalTestLogEntity) o;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (totalTestTaskId != null ? !totalTestTaskId.equals(that.totalTestTaskId) : that.totalTestTaskId != null)
            return false;
        if (testContent != null ? !testContent.equals(that.testContent) : that.testContent != null) return false;
        if (expectLabel != null ? !expectLabel.equals(that.expectLabel) : that.expectLabel != null) return false;
        if (expectRole != null ? !expectRole.equals(that.expectRole) : that.expectRole != null) return false;
        if (recLabel != null ? !recLabel.equals(that.recLabel) : that.recLabel != null) return false;
        if (recRule != null ? !recRule.equals(that.recRule) : that.recRule != null) return false;
        if (recRole != null ? !recRole.equals(that.recRole) : that.recRole != null) return false;
        if (module != null ? !module.equals(that.module) : that.module != null) return false;
        if (score != null ? !score.equals(that.score) : that.score != null) return false;
        if (pass != null ? !pass.equals(that.pass) : that.pass != null) return false;
        if (createTime != null ? !createTime.equals(that.createTime) : that.createTime != null) return false;
        if (companyId != null ? !companyId.equals(that.companyId) : that.companyId != null) return false;
        return true;
    }
}
