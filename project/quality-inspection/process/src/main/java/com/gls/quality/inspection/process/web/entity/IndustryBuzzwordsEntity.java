package com.gls.quality.inspection.process.web.entity;

import com.gls.starter.data.jpa.annotations.Comment;
import com.gls.starter.data.jpa.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

/**
 * @author george
 */
@Entity
@Data
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Comment("配置-行业热词")
public class IndustryBuzzwordsEntity extends BaseEntity {
    @Comment("行业类型id")
    @ManyToOne
    private IndustryCategoryEntity industryCategory;
    @Column(length = 50)
    @Comment("行业类型多级id 逗号分隔")
    private String industryCategoryIds;
    @Lob
    @Comment("热词")
    private String buzzwordsText;
    @Comment("热词数量")
    private Integer buzzwordsNumber;
}
