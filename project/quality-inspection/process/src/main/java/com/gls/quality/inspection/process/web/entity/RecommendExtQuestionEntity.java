package com.gls.quality.inspection.process.web.entity;

import com.gls.starter.data.jpa.annotations.Comment;
import com.gls.starter.data.jpa.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * @author george
 */
@Entity
@Data
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Comment("推荐扩展问")
public class RecommendExtQuestionEntity extends BaseEntity {
    @Comment("行业分类")
    private String industryCategory;
    @Comment("行业分类id，从父id到子id 1,2,3,4,5")
    private String industryCategoryIds;
    @Comment("语义标签")
    private Long semanticLabelId;
    @Column(length = 6000)
    @Comment("句式")
    private String phrasing;
    @Column(length = 6000)
    @Comment("涉及词集")
    private String wordSet;
}
