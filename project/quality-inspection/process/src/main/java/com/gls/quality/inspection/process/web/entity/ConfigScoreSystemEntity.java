package com.gls.quality.inspection.process.web.entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "tb_config_score_system", schema = "ai_quality_check", catalog = "")
public class ConfigScoreSystemEntity {
    private Integer id;
    private Byte scoreStrategy;
    private Byte scoreAttribute;
    private Integer defaultScore;
    private Byte scoreBaseline;
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
    @Column(name = "score_strategy")
    public Byte getScoreStrategy() {
        return scoreStrategy;
    }

    public void setScoreStrategy(Byte scoreStrategy) {
        this.scoreStrategy = scoreStrategy;
    }

    @Basic
    @Column(name = "score_attribute")
    public Byte getScoreAttribute() {
        return scoreAttribute;
    }

    public void setScoreAttribute(Byte scoreAttribute) {
        this.scoreAttribute = scoreAttribute;
    }

    @Basic
    @Column(name = "default_score")
    public Integer getDefaultScore() {
        return defaultScore;
    }

    public void setDefaultScore(Integer defaultScore) {
        this.defaultScore = defaultScore;
    }

    @Basic
    @Column(name = "score_baseline")
    public Byte getScoreBaseline() {
        return scoreBaseline;
    }

    public void setScoreBaseline(Byte scoreBaseline) {
        this.scoreBaseline = scoreBaseline;
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
        result = 31 * result + (scoreStrategy != null ? scoreStrategy.hashCode() : 0);
        result = 31 * result + (scoreAttribute != null ? scoreAttribute.hashCode() : 0);
        result = 31 * result + (defaultScore != null ? defaultScore.hashCode() : 0);
        result = 31 * result + (scoreBaseline != null ? scoreBaseline.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (updateTime != null ? updateTime.hashCode() : 0);
        result = 31 * result + (companyId != null ? companyId.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConfigScoreSystemEntity that = (ConfigScoreSystemEntity) o;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (scoreStrategy != null ? !scoreStrategy.equals(that.scoreStrategy) : that.scoreStrategy != null)
            return false;
        if (scoreAttribute != null ? !scoreAttribute.equals(that.scoreAttribute) : that.scoreAttribute != null)
            return false;
        if (defaultScore != null ? !defaultScore.equals(that.defaultScore) : that.defaultScore != null) return false;
        if (scoreBaseline != null ? !scoreBaseline.equals(that.scoreBaseline) : that.scoreBaseline != null)
            return false;
        if (createTime != null ? !createTime.equals(that.createTime) : that.createTime != null) return false;
        if (updateTime != null ? !updateTime.equals(that.updateTime) : that.updateTime != null) return false;
        if (companyId != null ? !companyId.equals(that.companyId) : that.companyId != null) return false;
        return true;
    }
}
